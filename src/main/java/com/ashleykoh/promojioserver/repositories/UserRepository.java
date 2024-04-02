package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUsersByUsername(String username);
    User findUserById(String id);
}
