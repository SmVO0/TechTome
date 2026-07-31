package com.SVO.TechTome.services;

import com.SVO.TechTome.config.EcontConfig;
import com.SVO.TechTome.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EcontDeliveryService implements DeliveryService {

    private static final BigDecimal DEFAULT_DELIVERY_COST = new BigDecimal("3.99");
    private static final double DEFAULT_WEIGHT = 1.0;

    private final RestTemplate restTemplate;
    private final EcontConfig config;

    public EcontDeliveryService(RestTemplate restTemplate, EcontConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    @Override
    public String createShipment(Order order) {
        try {
            LabelRequest request = buildRequest(order, "create");
            LabelResponse response = post(request);

            if (response == null || response.label() == null || response.label().shipmentNumber() == null) {
                log.warn("Econt returned empty response for order [{}].", order.getId());
                return null;
            }

            String tracking = response.label().shipmentNumber();
            log.info("Econt shipment created: tracking [{}] for order [{}].", tracking, order.getId());
            return tracking;

        } catch (Exception e) {
            log.warn("Econt shipment creation failed for order [{}]: {}", order.getId(), e.getMessage());
            return null;
        }
    }

    @Override
    public BigDecimal calculateDeliveryCost(String cityName) {
        try {
            Order placeholder = new Order();
            placeholder.setDeliveryCity(cityName);
            placeholder.setRecipientName("TechTome Calculation");
            placeholder.setRecipientPhone(config.getSender().getPhone());
            placeholder.setDeliveryAddress("1");

            LabelRequest request = buildRequest(placeholder, "calculate");
            LabelResponse response = post(request);

            if (response != null && response.label() != null && response.label().services() != null
                    && response.label().services().cdAmount() != null) {
                return response.label().services().cdAmount();
            }
        } catch (Exception e) {
            log.warn("Econt cost calculation failed for city [{}]: {}", cityName, e.getMessage());
        }
        return DEFAULT_DELIVERY_COST;
    }

    private LabelResponse post(LabelRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String url = config.getApiUrl() + "/Shipments/LabelService.createLabel.json";
        return restTemplate.postForObject(url, new HttpEntity<>(request, headers), LabelResponse.class);
    }

    private LabelRequest buildRequest(Order order, String mode) {
        Client senderClient = new Client(config.getSender().getName(), List.of(config.getSender().getPhone()));
        Address senderAddress = new Address(new City(config.getSender().getCity()));

        Client receiverClient = new Client(order.getRecipientName(), List.of(order.getRecipientPhone()));
        Address receiverAddress = new Address(new City(order.getDeliveryCity()));

        Label label = new Label(
                senderClient, senderAddress,
                receiverClient, receiverAddress,
                "PACK", DEFAULT_WEIGHT, 1,
                "Electronics order #" + order.getId()
        );
        return new LabelRequest(label, mode);
    }

    // ── DTOs ─────────────────────────────────────────────────────────────────

    record LabelRequest(Label label, String mode) {}

    record Label(Client senderClient, Address senderAddress,
                 Client receiverClient, Address receiverAddress,
                 String shipmentType, double weight, int packCount,
                 String shipmentDescription) {}

    record Client(String name, List<String> phones) {}

    record Address(City city) {}

    record City(String name) {}

    record LabelResponse(ShipmentStatus label, String courierRequestID) {}

    record ShipmentStatus(String shipmentNumber, LabelServices services) {}

    record LabelServices(BigDecimal cdAmount) {}
}
