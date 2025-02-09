package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.PlanType;
import com.cdacProject.taskhive.model.Subscription;
import com.cdacProject.taskhive.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);



}
