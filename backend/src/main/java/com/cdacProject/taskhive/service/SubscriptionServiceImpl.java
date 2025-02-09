package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.PlanType;
import com.cdacProject.taskhive.model.Subscription;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    @Autowired
    private UserService userService;



    @Override
    public Subscription createSubscription(User user) {

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(6));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return  subscriptionRepository.save(subscription);

    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(6));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }

        return subscriptionRepository.save(subscription);

    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {

        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }else{
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {

        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
