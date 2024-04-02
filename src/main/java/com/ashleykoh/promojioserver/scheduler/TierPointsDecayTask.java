package com.ashleykoh.promojioserver.scheduler;

import com.ashleykoh.promojioserver.repositories.CustomUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TierPointsDecayTask {

    @Autowired
    private CustomUserRepositoryImpl customUserRepository;

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void decayTierPoints() {
        customUserRepository.decrementUsersTierPoints();
    }
}
