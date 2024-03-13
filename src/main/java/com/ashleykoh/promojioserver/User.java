package com.ashleykoh.promojioserver;

import org.springframework.data.annotation.Id;

enum MembershipTiers {
    BRONZE,

}

public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String membershipTiers;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipTiers = "bronze";
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMembershipTiers() {
        return membershipTiers;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMembershipTiers(String membershipTiers) {
        this.membershipTiers = membershipTiers;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', firstName='%s', lastName='%s', membershipTier='%s']",
                id, firstName, lastName, membershipTiers
        );
    }

}
