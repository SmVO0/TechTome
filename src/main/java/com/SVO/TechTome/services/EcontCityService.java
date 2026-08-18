package com.SVO.TechTome.services;

import com.SVO.TechTome.config.EcontConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class EcontCityService {

    private static final int MAX_RESULTS = 10;

    private final RestTemplate restTemplate;
    private final EcontConfig config;
    private final AtomicReference<List<CityOption>> cities = new AtomicReference<>(List.of());

    public EcontCityService(RestTemplate restTemplate, EcontConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    @Scheduled(initialDelay = 0, fixedDelay = 6 * 60 * 60 * 1000)
    public void loadCities() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(config.getUsername(), config.getPassword());
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            String url = config.getApiUrl() + "/Nomenclatures/NomenclaturesService.getCities.json";
            NomenclatureResponse response = restTemplate.postForObject(
                    url, new HttpEntity<>("{}", headers), NomenclatureResponse.class);

            if (response != null && response.cities() != null) {
                List<CityOption> loaded = response.cities().stream()
                        .filter(c -> c.name() != null && c.postCode() != null)
                        .map(c -> new CityOption(c.name(), c.postCode()))
                        .toList();
                cities.set(loaded);
                log.info("Loaded {} cities from Econt nomenclature.", loaded.size());
            }
        } catch (Exception e) {
            log.warn("Could not load Econt city nomenclature: {}", e.getMessage());
        }
    }

    public boolean isValidCity(String name, String postCode) {
        List<CityOption> snapshot = cities.get();
        if (snapshot.isEmpty()) return true;
        return snapshot.stream()
                .anyMatch(c -> c.name().equals(name) && c.postCode().equals(postCode));
    }

    public List<CityOption> search(String query) {
        if (query == null || query.isBlank()) return List.of();
        String q = query.toLowerCase();
        return cities.get().stream()
                .filter(c -> c.name().toLowerCase().contains(q))
                .limit(MAX_RESULTS)
                .toList();
    }

    public record CityOption(String name, String postCode) {}

    record NomenclatureResponse(List<EcontCity> cities) {}

    record EcontCity(String name, String postCode) {}
}
