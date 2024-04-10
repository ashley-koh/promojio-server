package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.Promo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("/seed")
public class SeedController extends BaseController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("classpath:seed/promos.json")
    Resource promosResource;

    @GetMapping("/promos")
    public ResponseEntity<Map<String, Object>> seedPromos() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode promosNode = objectMapper.readTree(promosResource.getInputStream());

            for ( JsonNode promoNode : promosNode ) {
                String brand = promoNode.get("brand").asText();
                String smallLabel = promoNode.get("smallLabel").asText();
                String bigLabel = promoNode.get("bigLabel").asText();
                String shortDescription = promoNode.get("shortDescription").asText();
                String longDescription = promoNode.get("longDescription").asText();
                String validity = promoNode.get("validity").asText();
                String category = promoNode.get("category").asText();
                int points = promoNode.get("points").asInt();

                Promo promo = new Promo();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                promo.setBrand(brand);
                promo.setSmallLabel(smallLabel);
                promo.setBigLabel(bigLabel);
                promo.setShortDescription(shortDescription);
                promo.setLongDescription(longDescription);
                promo.setValidity(formatter.parse(validity));
                promo.setPoints(points);
                promo.setCategory(category);

                mongoTemplate.save(promo, "promo");
            }

        } catch (IOException ex) {
            return errorResponse(ex.getMessage());
        } catch (ParseException e) {
            throw new ServerRuntimeException("parseException", e.getMessage());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("promos", "seeded");

        return successResponse(data);
    }
}
