package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.Promo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoRepository extends MongoRepository<Promo, String> {

    Boolean findPromoById(String id);

}
