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

    @Scheduled(cron = "* */2 * * * *") // Run every hour
    public void decayTierPoints() {
        // find all documents that have tierPoints more than decreaseBy
        // this prevents tierPoints from going negative
        int decreaseBy = 10;
        Query query = new Query(Criteria.where("tierPoints").gte(decreaseBy));

        Update update = new Update();
        update.inc("tierPoints", -decreaseBy); // increment by -decreaseBy

        mongoTemplate.updateMulti(query, update, User.class);
    }
}
