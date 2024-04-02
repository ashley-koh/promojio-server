package com.ashleykoh.promojioserver.scheduler;

import com.ashleykoh.promojioserver.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TierPointsDecayTask {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void decayTierPoints() {
        // find all documents that have tierPoints more than 1
        // this prevents tierPoints from going negative
        Query query = new Query(Criteria.where("tierPoints").gte(1));

        Update update = new Update();
        update.inc("tierPoints", -1); // increment by -1

        mongoTemplate.updateMulti(query, update, User.class);
    }
}
