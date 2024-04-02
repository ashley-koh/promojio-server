package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> getLeaderboard() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "tierPoints"));
        query.limit(10);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void decrementUsersTierPoints() {
        // find all documents that have tierPoints more than 1
        // this prevents tierPoints from going negative
        Query query = new Query(Criteria.where("tierPoints").gte(1));

        Update update = new Update();
        update.inc("tierPoints", -1); // increment by -1

        mongoTemplate.updateMulti(query, update, User.class);
    }
}
