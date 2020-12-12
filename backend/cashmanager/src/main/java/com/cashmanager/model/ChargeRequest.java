package com.cashmanager.model;

import javax.persistence.*;

@Entity()
@Table(name = "charge", schema = "public")
public class ChargeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;
    private int amount;
    private String stripeEmail;
    private String stripeToken;

    // Get methods
    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    // Set methods
    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }


}