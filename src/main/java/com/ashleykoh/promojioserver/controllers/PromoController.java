package com.ashleykoh.promojioserver.controllers;


import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.repositories.PromoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/promo")
public class PromoController extends BaseController {

    @Autowired
    private PromoRepository promoRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPromo(@RequestBody @Valid Promo promo) {
        promoRepository.save(promo);
        Map<String, Object> data = new HashMap<>();
        data.put("user", promo);

        return successResponse(data);
    }

}
