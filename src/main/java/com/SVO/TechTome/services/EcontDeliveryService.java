package com.SVO.TechTome.services;

import com.SVO.TechTome.config.EcontConfig;
import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.web.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static com.SVO.TechTome.constants.ExceptionMessages.INVALID_DELIVERY_STREET;

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

        } catch (HttpStatusCodeException e) {
            if (e.getResponseBodyAsString().contains("ExInvalidAddress")) {
                throw new DomainException(INVALID_DELIVERY_STREET);
            }
            log.warn("Econt shipment creation failed for order [{}]: {}", order.getId(), e.getMessage());
            return null;
        } catch (Exception e) {
            log.warn("Econt shipment creation failed for order [{}]: {}", order.getId(), e.getMessage());
            return null;
        }
    }

    @Override
    public BigDecimal calculateDeliveryCost(String cityName, String postCode, String street, String num, String other) {
        try {
            Order placeholder = new Order();
            placeholder.setDeliveryCity(cityName);
            placeholder.setDeliveryPostCode(postCode);
            placeholder.setRecipientName("TechTome Calculation");
            placeholder.setRecipientPhone(config.getSender().getPhone());
            placeholder.setDeliveryStreet(street);
            placeholder.setDeliveryNum(num);
            placeholder.setDeliveryOther(other);

            LabelRequest request = buildRequest(placeholder, "calculate");
            LabelResponse response = post(request);

            if (response != null && response.label() != null && response.label().totalPrice() != null) {
                return response.label().totalPrice();
            }
        } catch (HttpStatusCodeException e) {
            if (e.getResponseBodyAsString().contains("ExInvalidAddress")) {
                throw new DomainException(INVALID_DELIVERY_STREET);
            }
            log.warn("Econt cost calculation failed for city [{}]: {}", cityName, e.getMessage());
        } catch (Exception e) {
            log.warn("Econt cost calculation failed for city [{}]: {}", cityName, e.getMessage());
        }
        return DEFAULT_DELIVERY_COST;
    }

    @Override
    public TrackingStatus getTrackingStatus(String trackingNumber) {
        try {
            TrackingRequest request = new TrackingRequest(List.of(trackingNumber));
            String url = config.getApiUrl() + "/Tracking/LabelService.getShipmentStatuses.json";
            TrackingResponse response = restTemplate.postForObject(
                    url, new HttpEntity<>(request, buildHeaders()), TrackingResponse.class);

            if (response != null && response.shipmentStatuses() != null && !response.shipmentStatuses().isEmpty()) {
                ShipmentEvent event = response.shipmentStatuses().get(0);
                return new TrackingStatus(event.lastOperationDescription(), event.lastOperationDate());
            }
        } catch (Exception e) {
            log.warn("Econt tracking lookup failed for [{}]: {}", trackingNumber, e.getMessage());
        }
        return null;
    }

    private LabelResponse post(LabelRequest request) {
        log.debug("Econt request: sender city={} postCode={}",
                request.label().senderAddress().city().name(),
                request.label().senderAddress().city().postCode());

        String url = config.getApiUrl() + "/Shipments/LabelService.createLabel.json";
        return restTemplate.postForObject(url, new HttpEntity<>(request, buildHeaders()), LabelResponse.class);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private LabelRequest buildRequest(Order order, String mode) {
        Country country = new Country("BGR");

        Client senderClient = new Client(config.getSender().getName(), List.of(config.getSender().getPhone()));
        Address senderAddress = new Address(
                new City(country, config.getSender().getCity(), config.getSender().getPostCode()),
                config.getSender().getStreet(), config.getSender().getNum(), null);

        List<String> receiverPhones = (order.getRecipientPhone() != null && !order.getRecipientPhone().isBlank())
                ? List.of(order.getRecipientPhone()) : List.of();
        Client receiverClient = new Client(order.getRecipientName(), receiverPhones);

        Label label;
        if (order.getDeliveryOfficeCode() != null) {
            label = new Label(senderClient, senderAddress, receiverClient,
                    null, order.getDeliveryOfficeCode(),
                    "PACK", DEFAULT_WEIGHT, 1, "Electronics order #" + order.getId());
        } else {
            Address receiverAddress = new Address(
                    new City(country, order.getDeliveryCity(), order.getDeliveryPostCode()),
                    order.getDeliveryStreet(), order.getDeliveryNum(), order.getDeliveryOther());
            label = new Label(senderClient, senderAddress, receiverClient,
                    receiverAddress, null,
                    "PACK", DEFAULT_WEIGHT, 1, "Electronics order #" + order.getId());
        }
        return new LabelRequest(label, mode);
    }

    // ── DTOs ─────────────────────────────────────────────────────────────────

    record LabelRequest(Label label, String mode) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Label(Client senderClient, Address senderAddress,
                 Client receiverClient, Address receiverAddress,
                 String receiverOfficeCode,
                 String shipmentType, double weight, int packCount,
                 String shipmentDescription) {}

    record Client(String name, List<String> phones) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Address(City city, String street, String num, String other) {}

    record City(Country country, String name, String postCode) {}

    record Country(String code3) {}

    record LabelResponse(ShipmentStatus label, String courierRequestID) {}

    record ShipmentStatus(String shipmentNumber, BigDecimal totalPrice) {}

    record TrackingRequest(List<String> shipmentNumbers) {}

    record TrackingResponse(List<ShipmentEvent> shipmentStatuses) {}

    record ShipmentEvent(String shipmentNumber, String lastOperationDescription, String lastOperationDate) {}
}
