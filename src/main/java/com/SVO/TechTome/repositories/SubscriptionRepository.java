package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.Subscription;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByOwnerAndStatus(User owner, SubscriptionStatus status);
}