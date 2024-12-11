package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Incentive {

    private float amount;

    // Default constructor
    public Incentive() {
    }

    public Incentive(float amount) {
        this.amount = amount;
    }

    // Getter and Setter for amount
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{amount=" + amount + "}";
    }
}