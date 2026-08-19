package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Subscription;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.SubscriptionStatus;
import com.SVO.TechTome.models.enums.SubscriptionType;
import com.SVO.TechTome.repositories.SubscriptionRepository;
import com.SVO.TechTome.web.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class SubscriptionService {

    private static final BigDecimal FREE_SHIPPING_THRESHOLD_STANDARD = new BigDecimal("50.00");

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createDefaultSubscription(User user) {
        Subscription subscription = subscriptionRepository.save(initializeSubscription(user));
        log.info("New subscription with id [{}] and type [{}].", subscription.getId(), subscription.getType());
        return subscription;
    }

    public Subscription getActiveSubscription(User user) {
        return subscriptionRepository.findByOwnerAndStatus(user, SubscriptionStatus.ACTIVE)
                .orElseGet(() -> initializeSubscription(user));
    }

    @Transactional
    public void upgrade(User user, SubscriptionType newType) {
        Subscription current = getActiveSubscription(user);

        if (current.getType().ordinal() >= newType.ordinal()) {
            throw new DomainException("Cannot downgrade or select the same subscription tier.");
        }

        changeTier(user, current, newType);
        log.info("User [{}] upgraded subscription to [{}].", user.getEmail(), newType);
    }

    @Transactional
    public void downgrade(User user, SubscriptionType newType) {
        Subscription current = getActiveSubscription(user);

        if (current.getType().ordinal() <= newType.ordinal()) {
            throw new DomainException("Cannot upgrade or select the same subscription tier.");
        }

        changeTier(user, current, newType);
        log.info("User [{}] downgraded subscription to [{}].", user.getEmail(), newType);
    }

    private void changeTier(User user, Subscription current, SubscriptionType newType) {
        current.setStatus(SubscriptionStatus.INACTIVE);
        subscriptionRepository.save(current);

        Subscription next = Subscription.builder()
                .owner(user)
                .type(newType)
                .status(SubscriptionStatus.ACTIVE)
                .price(BigDecimal.ZERO)
                .createdOn(LocalDateTime.now())
                .completedOn(LocalDateTime.now().plusMonths(1))
                .build();
        subscriptionRepository.save(next);
    }

    public BigDecimal getDiscountMultiplier(User user) {
        SubscriptionType type = getActiveSubscription(user).getType();
        return switch (type) {
            case STANDARD -> new BigDecimal("0.95");
            case PREMIUM  -> new BigDecimal("0.90");
            default       -> BigDecimal.ONE;
        };
    }

    public boolean isShippingFree(User user, BigDecimal orderTotal) {
        SubscriptionType type = getActiveSubscription(user).getType();
        return switch (type) {
            case PREMIUM  -> true;
            case STANDARD -> orderTotal.compareTo(FREE_SHIPPING_THRESHOLD_STANDARD) >= 0;
            default       -> false;
        };
    }

    private Subscription initializeSubscription(User user) {
        LocalDateTime now = LocalDateTime.now();
        return Subscription.builder()
                .owner(user)
                .status(SubscriptionStatus.ACTIVE)
                .type(SubscriptionType.DEFAULT)
                .price(BigDecimal.ZERO)
                .createdOn(now)
                .completedOn(now.plusMonths(1))
                .build();
    }
}
