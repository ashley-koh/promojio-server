package com.ashleykoh.promojioserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByFirstName(String firstName);
    List<User> findUsersByLastName(String lastName);
}
