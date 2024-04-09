package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.models.User;
import com.mongodb.client.result.UpdateResult;
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
    public void updateUserName(String id, String name) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name", name);

        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "value unchanged");
        }
    }

    @Override
    public void updateUserPoints(String id, int points) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("points", points);

        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "value unchanged");
        }
    }

    @Override
    public void updateUserTierPoints(String id, int tierPoints) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("tierPoints", tierPoints);

        if (tierPoints < 1000) {
            update.set("memberTier", "bronze");
        } else if (tierPoints < 2000) {
            update.set("memberTiers", "silver");
        } else if (tierPoints < 3000) {
            update.set("memberTiers", "gold");
        } else {
            update.set("memberTiers", "platinum");
        }

        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "value unchanged");
        }
    }

    @Override
    public void addPromoToUser(String id, String promo_id) {
        Query promoQuery = new Query(Criteria.where("id").is(promo_id));
        Promo promo = mongoTemplate.findOne(promoQuery, Promo.class);

        if (promo == null) {
            throw new ServerRuntimeException("promoId", "promo does not exist");
        }

        Query userQuery = new Query(Criteria.where("id").is(id));
        Update userUpdate = new Update();
        userUpdate.push("promos", promo);

        UpdateResult result = mongoTemplate.updateFirst(userQuery, userUpdate, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "value unchanged");
        }
    }


}
