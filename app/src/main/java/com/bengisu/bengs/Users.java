package com.bengisu.bengs;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Users {
    public String name;
    public String surname;

    public Users() {
    }

    public Users(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
