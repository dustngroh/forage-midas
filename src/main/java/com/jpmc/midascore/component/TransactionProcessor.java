package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcessor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;

    @KafkaListener(topics = "${general.kafka-topic}")
    @Transactional
    public void processTransaction(Transaction transaction) {

        // Get sender, recipient, and amount from Transaction
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
        float amount = transaction.getAmount();

        // Validate sender and recipient
        if (sender == null || recipient == null) {
            System.out.println("Invalid sender or recipient");
            return; // Invalid sender or recipient
        }

        // Validate sender's balance
        if (sender.getBalance() < amount) {
            System.out.println("Sender does not have high enough balance");
            return; // Sender does not have enough balance
        }

        // Process transaction
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        // Fetch incentive for the recipient
        Incentive incentive = incentiveService.getIncentiveForTransaction(transaction);

        // Add incentive amount to recipient's balance
        if (incentive != null && incentive.getAmount() > 0) {
            recipient.setBalance(recipient.getBalance() + incentive.getAmount());  // Add incentive to recipient balance
        }

        // Save transaction record
        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, amount, incentive.getAmount());
        transactionRepository.save(transactionRecord);

        // Save updated user records
        userRepository.save(sender);
        userRepository.save(recipient);

        System.out.println("Successfully processed transaction");
    }
}
