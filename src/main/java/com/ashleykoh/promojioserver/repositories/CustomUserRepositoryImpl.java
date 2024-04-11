package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.models.User;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
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
    public void incrementUserPoints(String id, int incPoints) {
        User user = mongoTemplate.findById(id, User.class);
        int newPoints = user.getPoints() + incPoints;

        updateUserPoints(id, Math.max(newPoints, 0));
    }

    @Override
    public void updateUserTierPoints(String id, int tierPoints) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("tierPoints", tierPoints);

        String memberTier = "memberTier";

        if (tierPoints < 1000) {
            update.set(memberTier, "bronze");
        } else if (tierPoints < 2000) {
            update.set(memberTier, "silver");
        } else if (tierPoints < 3000) {
            update.set(memberTier, "gold");
        } else {
            update.set(memberTier, "platinum");
        }

        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "value unchanged");
        }
    }

    @Override
    public void incrementUserTierPoints(String id, int incTierPoints) {
        User user = mongoTemplate.findById(id, User.class);

        int newTierPoints = user.getTierPoints() + incTierPoints;

        updateUserTierPoints(id, Math.max(newTierPoints, 0));
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

    @Override
    public void usePromo(String id, String promo_id) {
        Query userQuery = new Query(Criteria.where("id").is(id));
        Update userUpdate = new Update();

        Query promoWithinUserQuery = new Query().addCriteria(Criteria.where("$id").is(new ObjectId(promo_id)));
        userUpdate.pull("promos", promoWithinUserQuery); // remove promo from user document

        UpdateResult result = mongoTemplate.updateFirst(userQuery, userUpdate, User.class);

        if (result.getModifiedCount() == 0) {
            throw new ServerRuntimeException("updated", "user does not have promo");
        }

        User user = mongoTemplate.findById(id, User.class);
        Promo promo = mongoTemplate.findById(promo_id, Promo.class);

        if (promo == null) {
            throw new ServerRuntimeException("updated", "promo no longer exists");
        }

        if (user == null) {
            throw new ServerRuntimeException("updated", "user does not exist");
        }

        updateUserPoints(id, user.getPoints() + promo.getPoints()); // rewards points from promo to user
    }
}
