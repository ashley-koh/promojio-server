package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserRepository userRespository;
}
