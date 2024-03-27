package com.ashleykoh.promojioserver.models;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String name;
    private String username;
    private String passwordHash;
    private Binary profileImage;
    private int points;
    private int tierPoints;
    private String memberTier;
    @DBRef
    private List<Promo> promos = new ArrayList<>();

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = "";
        this.profileImage = null;
        this.points = 0;
        this.tierPoints = 0;
        this.memberTier = "bronze";
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImage(Binary profileImage) {
        this.profileImage = profileImage;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMemberTier(String memberTier) {
        this.memberTier = memberTier;
    }

    public void setTierPoints(int tierPoints) {
        this.tierPoints = tierPoints;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }

    @Override
    public String toString() {
        return String.format(
                """
                User:
                id='%s'
                username='%s'
                memberTier=%s
                points=%d
                tierPoints=%d
                """,
                id, username, memberTier, points, tierPoints
        );
    }

}
