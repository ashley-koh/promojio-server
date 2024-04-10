package com.ashleykoh.promojioserver.scheduler;

import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromoDistributionTask {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Scheduled(cron = "0 */10 * * * *") // Run every 10 mins
    public void distributePromos() {

        // construct aggregation query
        LocalDate oneWeekFromNow = LocalDate.now().plusDays(7);
        MatchOperation validity = new MatchOperation(Criteria.where("validity").gte(oneWeekFromNow));
        Aggregation agg = Aggregation.newAggregation(
                validity,
                new SampleOperation(5)
        );

        // get all users
        List<User> users = mongoTemplate.findAll(User.class);

        for (User user : users) {
            // execute aggregation to sample 5 random Promos
            AggregationResults<Promo> results = mongoTemplate.aggregate(agg, "promo", Promo.class);
            List<Promo> mappedResult = results.getMappedResults();

            // set promos in user
            user.setPromos(mappedResult);

            mongoTemplate.save(user);
        }
    }
}
