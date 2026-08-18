package com.SVO.TechTome.services;

import com.SVO.TechTome.config.EcontConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class EcontOfficeService {

    private static final int MAX_RESULTS = 10;

    private final RestTemplate restTemplate;
    private final EcontConfig config;
    private final AtomicReference<List<OfficeOption>> offices = new AtomicReference<>(List.of());

    public EcontOfficeService(@Qualifier("nomenclaturesRestTemplate") RestTemplate restTemplate, EcontConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    @Scheduled(initialDelay = 0, fixedDelay = 6 * 60 * 60 * 1000)
    public void loadOffices() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(config.getUsername(), config.getPassword());
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            String url = config.getApiUrl() + "/Nomenclatures/NomenclaturesService.getOffices.json";
            OfficeResponse response = restTemplate.postForObject(
                    url, new HttpEntity<>("{}", headers), OfficeResponse.class);

            if (response != null && response.offices() != null) {
                List<OfficeOption> loaded = response.offices().stream()
                        .filter(o -> o.code() != null && o.name() != null
                                && o.address() != null && o.address().city() != null
                                && o.address().city().name() != null)
                        .map(o -> new OfficeOption(o.code(), o.name(), o.address().city().name()))
                        .toList();
                offices.set(loaded);
                log.info("Loaded {} offices from Econt nomenclature.", loaded.size());
            }
        } catch (Exception e) {
            log.warn("Could not load Econt office nomenclature: {}", e.getMessage());
        }
    }

    public List<OfficeOption> search(String query) {
        if (query == null || query.isBlank()) return List.of();
        String q = query.toLowerCase();
        return offices.get().stream()
                .filter(o -> o.city().toLowerCase().contains(q) || o.name().toLowerCase().contains(q))
                .limit(MAX_RESULTS)
                .toList();
    }

    public record OfficeOption(String code, String name, String city) {}

    record OfficeResponse(List<EcontOffice> offices) {}

    record EcontOffice(String code, String name, EcontOfficeAddress address) {}

    record EcontOfficeAddress(EcontOfficeCity city) {}

    record EcontOfficeCity(String name) {}
}
