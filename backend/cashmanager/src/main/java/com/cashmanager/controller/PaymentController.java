package com.cashmanager.controller;

import com.cashmanager.model.ChargeRequest;
import com.cashmanager.model.PaymentIntentRequest;
import com.cashmanager.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/charge")
    public String chargeCard(ChargeRequest chargeRequest) throws StripeException {
        String token = chargeRequest.getStripeToken();
        int amount = chargeRequest.getAmount();
        String description = chargeRequest.getDescription();
        stripeService.chargeNewCard(token, amount, description);
        return "result";
    }

    @PostMapping("/payment_intents")
    public String createPaymentIntent(PaymentIntentRequest paymentIntentRequest) throws StripeException {
        System.out.println("xxxxx -> route Ok");
        /*amount // paymentMethodTypes[] // currency*/
        int amount = paymentIntentRequest.getAmount();
        String paymentMethodTypes = paymentIntentRequest.getPaymentMethodTypes();
        var secret = stripeService.createPaymentIntent(amount, paymentMethodTypes);
        return secret;
    }
}
