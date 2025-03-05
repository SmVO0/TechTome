package com.SVO.TechTome.subscription.service;

import com.SVO.TechTome.subscription.model.Subscription;
import com.SVO.TechTome.subscription.model.SubscriptionStatus;
import com.SVO.TechTome.subscription.model.SubscriptionType;
import com.SVO.TechTome.subscription.repository.SubscriptionRepository;
import com.SVO.TechTome.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    public Subscription createDefaultSubscription(User user) {

        Subscription subscription = subscriptionRepository.save(initializeSubscription(user));
        log.info("New subscription with id [%s] and type [%s].".formatted(subscription.getId(), subscription.getType()));

        return subscription;
    }

    private Subscription initializeSubscription(User user) {

        LocalDateTime now = LocalDateTime.now();

        return Subscription.builder()
                .owner(user)
                .status(SubscriptionStatus.ACTIVE)
                .type(SubscriptionType.DEFAULT)
                .price(new BigDecimal("0.00"))
                .createdOn(now)
                .completedOn(now.plusMonths(1))
                .build();
    }

}
