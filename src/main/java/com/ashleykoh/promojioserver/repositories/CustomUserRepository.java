package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.User;

import java.util.List;

public interface CustomUserRepository {

    List<User> getLeaderboard();

    void decrementUsersTierPoints();
}
