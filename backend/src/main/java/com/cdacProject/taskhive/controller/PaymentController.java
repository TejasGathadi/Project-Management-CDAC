package com.cdacProject.taskhive.controller;


import com.cdacProject.taskhive.model.PlanType;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.response.PaymentLinkResponse;
import com.cdacProject.taskhive.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;


    @Autowired
    private UserService userService;


    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        int amount = 499*100;
        if(planType.equals(PlanType.ANNUALLY)){
            amount = amount * 12;
            amount = (int)(amount*0.8); // giving 20 % off here to customer
        }

            RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());

            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType"+planType);

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_url(paymentLinkUrl);
            res.setPayment_link_id(paymentLinkId);

            return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

}
