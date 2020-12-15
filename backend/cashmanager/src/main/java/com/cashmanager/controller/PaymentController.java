package com.cashmanager.controller;

import com.cashmanager.model.PaymentIntentRequest;
import com.cashmanager.services.StripeService;
import com.cashmanager.services.mapper.ResponseTemplate;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    // Reading the value from the application.properties file
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @RequestMapping("/checkout")
    public String home(Model model) {
        model.addAttribute("amount", 50 * 100); // In cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "checkout";
    }

    @Autowired
    private StripeService stripeService;

    @PostMapping("/payment_intents")
    public Object createPaymentIntent(@RequestBody PaymentIntentRequest paymentIntentRequest) throws StripeException {
        try {
            Integer amount = paymentIntentRequest.getAmount();
            String paymentMethodTypes = paymentIntentRequest.getPaymentMethodTypes();
            var secret = stripeService.createPaymentIntent(amount, paymentMethodTypes);
            Map<String, String> result = new HashMap<>();
            result.put("client_secret", secret);
            return ResponseTemplate.success(result);

        } catch (Exception | Error error) {
            return ResponseTemplate.badRequest(error.getLocalizedMessage());
        }
    }
}
