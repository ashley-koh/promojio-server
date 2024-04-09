package com.ashleykoh.promojioserver.repositories;

import com.ashleykoh.promojioserver.models.Promo;

public interface CustomPromoRepository {
    Promo getRandomPromo(String min, String max);
}
