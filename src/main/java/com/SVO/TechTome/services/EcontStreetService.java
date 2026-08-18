package com.SVO.TechTome.services;

import com.SVO.TechTome.config.EcontConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class EcontStreetService {

    private static final int MAX_RESULTS = 10;

    private final RestTemplate restTemplate;
    private final EcontConfig config;
    private final ConcurrentHashMap<String, List<String>> streetCache = new ConcurrentHashMap<>();
    private final Set<String> inFlight = ConcurrentHashMap.newKeySet();

    public EcontStreetService(@Qualifier("nomenclaturesRestTemplate") RestTemplate restTemplate,
                              EcontConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    /** Start a background load for the given city if not already cached or in-flight. */
    public void preload(String cityName) {
        if (cityName != null && !cityName.isBlank()) {
            triggerLoad(cityName);
        }
    }

    public List<String> search(String cityName, String query) {
        if (cityName == null || cityName.isBlank() || query == null || query.isBlank()) return List.of();
        List<String> cached = streetCache.get(cityName);
        if (cached == null) {
            triggerLoad(cityName); // kick off load if not started yet
            return List.of();
        }
        String q = query.toLowerCase();
        return cached.stream()
                .filter(s -> s.toLowerCase().contains(q))
                .limit(MAX_RESULTS)
                .toList();
    }

    private void triggerLoad(String cityName) {
        if (streetCache.containsKey(cityName) || !inFlight.add(cityName)) return;
        CompletableFuture.runAsync(() -> {
            try {
                List<String> streets = loadFromEcont(cityName);
                streetCache.put(cityName, streets);
            } finally {
                inFlight.remove(cityName);
            }
        });
    }

    private List<String> loadFromEcont(String cityName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(config.getUsername(), config.getPassword());
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            StreetRequest body = new StreetRequest(new CityRef(cityName));
            StreetResponse response = restTemplate.postForObject(
                    config.getApiUrl() + "/Nomenclatures/NomenclaturesService.getStreets.json",
                    new HttpEntity<>(body, headers), StreetResponse.class);

            if (response != null && response.streets() != null) {
                List<String> names = response.streets().stream()
                        .map(EcontStreet::name)
                        .filter(n -> n != null && !n.isBlank())
                        .toList();
                log.info("Loaded {} streets for city '{}'.", names.size(), cityName);
                return names;
            }
        } catch (Exception e) {
            log.warn("Could not load streets for city '{}': {}", cityName, e.getMessage());
        }
        return List.of();
    }

    record StreetRequest(CityRef city) {}
    record CityRef(String name) {}
    record StreetResponse(List<EcontStreet> streets) {}
    record EcontStreet(String name) {}
}
