package com.ashleykoh.promojioserver.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Promo {
    @Id
    private String id;
    @NotBlank(message = "brand required")
    private String brand;
    private Binary logoImage;
    @NotNull(message = "small label required")
    private String smallLabel;
    @NotNull(message = "big label required")
    private String bigLabel;
    @NotBlank(message = "short description required")
    private String shortDescription;
    @NotBlank(message = "short description required")
    private String longDescription;
    @NotNull(message = "validity required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date validity;
    @NotNull(message = "points required")
    private int points;

    private String url;

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

    public String getSmallLabel() {
        return smallLabel;
    }

    public void setSmallLabel(String smallLabel) {
        this.smallLabel = smallLabel;
    }

    public String getBigLabel() {
        return bigLabel;
    }

    public void setBigLabel(String bigLabel) {
        this.bigLabel = bigLabel;
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
