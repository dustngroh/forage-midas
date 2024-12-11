package com.jpmc.midascore.component;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {

    private final RestTemplate restTemplate;

    @Autowired
    public IncentiveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Incentive getIncentiveForTransaction(Transaction transaction) {
        String url = "http://localhost:8080/incentive";
        return restTemplate.postForObject(url, transaction, Incentive.class);
    }
}