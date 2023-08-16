package com.example.cpscheduler;

public class usermode {
    private String name,leet,codef,email;

    public usermode(String name, String leet, String codef, String email) {
        this.name = name;
        this.leet = leet;
        this.codef = codef;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeet() {
        return leet;
    }

    public void setLeet(String leet) {
        this.leet = leet;
    }

    public String getCodef() {
        return codef;
    }

    public void setCodef(String codef) {
        this.codef = codef;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
