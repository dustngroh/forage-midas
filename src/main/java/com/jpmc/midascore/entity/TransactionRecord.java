package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Incentive;
import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = true)
    private float incentive;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }

    public long getId() {
        return id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentiveAmount(float incentive) {
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return String.format("TransactionRecord[id=%d, sender=%s, recipient=%s, amount=%.2f, incentiveAmount=%.2f]", id, sender.getName(), recipient.getName(), amount, incentive);
    }
}