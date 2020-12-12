package com.cashmanager.model;

import javax.persistence.*;

@Entity()
@Table(name = "intent", schema = "public")
public class PaymentIntentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int amount;
    private String paymentMethodTypes;

    public int getAmount() {
        return amount;
    }

    public String getPaymentMethodTypes() {
        return paymentMethodTypes;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPaymentMethodTypes(String payment_method_types) {
        this.paymentMethodTypes = payment_method_types;
    }
}
