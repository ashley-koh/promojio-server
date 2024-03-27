package com.ashleykoh.promojioserver.models;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Promo {
    @Id
    private String id;
    private String brand;
    private Binary logoImage;
    private int value;
    private String symbol;
    private String shortDescription;
    private String longDescription;
    private String[] termsAndConditions;
    private Date validity;
    private int points;
}
