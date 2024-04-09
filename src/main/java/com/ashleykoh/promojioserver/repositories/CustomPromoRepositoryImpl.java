package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.Promo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CustomPromoRepositoryImpl implements CustomPromoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // get points within range and randomly choose 1
    @Override
    public Promo getRandomPromo(String min, String max) {

        int minInt = 0;
        int maxInt = 0;
        if (min != null) { minInt = Integer.parseInt(min); }
        if (max != null) { maxInt = Integer.parseInt(max); }

        // make sure query lasts at least a week from now
        LocalDate oneWeekFromNow = LocalDate.now().plusDays(7);
        MatchOperation validity = new MatchOperation(Criteria.where("validity").gte(oneWeekFromNow));
        MatchOperation match;

        if (min != null && max != null) {
            match = new MatchOperation(Criteria.where("points").gte(minInt).lte(maxInt));
        } else if (min != null) {
            match = new MatchOperation(Criteria.where("points").gte(minInt));
        } else {
            match = new MatchOperation(Criteria.where("points").lte(maxInt));
        }

        // create aggregation
        Aggregation agg = Aggregation.newAggregation(
                validity,
                match,
                new SampleOperation(1)
        );

        // execute aggregation
        AggregationResults<Promo> results = mongoTemplate.aggregate(agg, "promo", Promo.class);
        List<Promo> mappedResult = results.getMappedResults();

        // get promo from result
        if (!mappedResult.isEmpty()) {
            return mappedResult.get(0);
        }

        // below only runs if promo not found from aggregation
        Promo lowerPromo = null;
        Promo higherPromo = null;


        // query to get next lowest
        Query lowerQuery = new Query();
        lowerQuery.addCriteria(Criteria.where("points").lt(minInt));
        lowerQuery.addCriteria(Criteria.where("validity").gte(oneWeekFromNow));
        lowerQuery.with(Sort.by(Sort.Direction.DESC, "points"));

        // query to get next highest
        Query higherQuery = new Query();
        higherQuery.addCriteria(Criteria.where("points").gt(maxInt));
        higherQuery.addCriteria(Criteria.where("validity").gte(oneWeekFromNow));
        higherQuery.with(Sort.by(Sort.Direction.ASC, "points"));

        // only query if min or max is not null respectively
        if (min != null) lowerPromo = mongoTemplate.findOne(lowerQuery, Promo.class);
        if (max != null) higherPromo = mongoTemplate.findOne(higherQuery, Promo.class);

        if (lowerPromo != null && higherPromo != null) {
            // Get positive deltas
            int lowerDelta = minInt - lowerPromo.getPoints();
            int higherDelta = higherPromo.getPoints() - maxInt;

            // return promo that is closer to the range
            if (lowerDelta < higherDelta)
                return lowerPromo;
            else
                return higherPromo;
        } else if (lowerPromo != null) {
            return lowerPromo; // return lower if higher is null
        } else {
            return higherPromo; // return higher if lower is null or null if both lower and higher are null
        }
    }
}
