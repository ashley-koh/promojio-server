package com.ashleykoh.promojioserver.models;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String name;
    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;
    private Binary profileImage;
    private int points;
    private int tierPoints;
    private String memberTier;
    @DBRef
    private List<Promo> promos = new ArrayList<>();

    public User() {}
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//        this.name = "";
//        this.profileImage = null;
//        this.points = 0;
//        this.tierPoints = 0;
//        this.memberTier = "bronze";
//    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfileImage(Binary profileImage) {
        this.profileImage = profileImage;
    }

    public Binary getProfileImage() {
        return profileImage;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setMemberTier(String memberTier) {
        this.memberTier = memberTier;
    }

    public String getMemberTier() {
        return memberTier;
    }

    public void setTierPoints(int tierPoints) {
        this.tierPoints = tierPoints;
    }

    public int getTierPoints() {
        return tierPoints;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }

    public List<Promo> getPromos() {
        return promos;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format(
                """
                User:
                id='%s'
                name='%s'
                username='%s'
                password='%s'
                memberTier=%s
                points=%d
                tierPoints=%d
                """,
                id, name, username, password, memberTier, points, tierPoints
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return points == user.points && tierPoints == user.tierPoints && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(profileImage, user.profileImage) && Objects.equals(memberTier, user.memberTier) && Objects.equals(promos, user.promos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, profileImage, points, tierPoints, memberTier, promos);
    }
}
