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
}
