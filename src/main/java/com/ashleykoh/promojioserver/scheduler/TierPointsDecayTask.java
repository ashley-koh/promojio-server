package com.ashleykoh.promojioserver.scheduler;

import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.CustomUserRepository;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierPointsDecayTask {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Scheduled(cron = "*/2 * * * * *") // Run every hour
    public void decayTierPoints() {
        // find all documents that have tierPoints more than decreaseBy
        // this prevents tierPoints from going negative
        int decreaseBy = 1;

        Query query = new Query(Criteria.where("tierPoints").gte(decreaseBy));
        List<User> users = mongoTemplate.find(query, User.class);

        for (User user : users) {
            customUserRepository.incrementUserTierPoints(user.getId(), -decreaseBy);
        }
    }
}
