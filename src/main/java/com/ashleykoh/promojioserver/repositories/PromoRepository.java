package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.Promo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface PromoRepository extends MongoRepository<Promo, String> {

    Promo findPromoById(String id);
    Promo findPromosByBrand(String brand);
    Promo findPromosByValidityBefore(Date date);

    Promo deletePromoById(String id);
}
