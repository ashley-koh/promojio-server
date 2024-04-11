package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.User;

import java.util.List;

public interface CustomUserRepository {

    List<User> getLeaderboard();

    void updateUserName(String id, String name);
    void updateUserPoints(String id, int points);
    void incrementUserPoints(String id, int incTierPoints);
    void updateUserTierPoints(String id, int tierPoints);
    void incrementUserTierPoints(String id, int incTierPoints);
    void addPromoToUser(String id, String promo_id);
    void usePromo(String id, String promo_id);
}
