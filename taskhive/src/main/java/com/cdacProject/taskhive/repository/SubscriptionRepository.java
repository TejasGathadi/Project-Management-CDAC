package com.cdacProject.taskhive.repository;

import com.cdacProject.taskhive.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    Subscription findByUserId(Long userId);

}
