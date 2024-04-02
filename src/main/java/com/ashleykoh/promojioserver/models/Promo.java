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

    public Promo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Binary getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(Binary logoImage) {
        this.logoImage = logoImage;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String[] getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String[] termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
