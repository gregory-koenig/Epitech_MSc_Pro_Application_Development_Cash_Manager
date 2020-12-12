package com.cashmanager.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge chargeNewCard(String token, int amount, String description) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "EUR");
        chargeParams.put("source", token);
        chargeParams.put("description", description);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

    public String createPaymentIntent(int amount, String paymentMethodTypes) throws StripeException {
        System.out.println("xxxxxx -> amount : " + amount);
        List<Object> paymentMethodTypesArray = new ArrayList<>();
        paymentMethodTypesArray.add(paymentMethodTypes);
        Map<String, Object> intentParams = new HashMap<String, Object>();
        intentParams.put("amount", (int)(amount * 100));
        intentParams.put("currency", "EUR");
        intentParams.put("payment_method_types", paymentMethodTypesArray);
        PaymentIntent paymentIntent = PaymentIntent.create(intentParams);
        var secret = paymentIntent.getClientSecret();
        return secret;
    }
}