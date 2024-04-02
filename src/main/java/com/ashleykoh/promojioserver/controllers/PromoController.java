package com.ashleykoh.promojioserver.controllers;


import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.repositories.PromoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPromos() {
        List<Promo> promos = promoRepository.findAll();
        return successResponse(promos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPromoById(@PathVariable String id) {
        Promo promo = promoRepository.findPromoById(id);

        if (promo == null) {
            return promoDoesNotExistResponse();
        }

        return successResponse(promo);
    }

}
