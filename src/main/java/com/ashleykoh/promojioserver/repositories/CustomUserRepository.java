package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.controllers.forms.UserDetails;
import com.ashleykoh.promojioserver.models.User;

import java.util.List;

public interface CustomUserRepository {

    List<User> getLeaderboard();

    void updateUserName(String id, String name);
    void updateUserPoints(String id, int points);
    void updateUserTierPoints(String id, int tierPoints);
    void addPromoToUser(String id, String promo_id);
    void usePromo(String id, String promo_id);
}
