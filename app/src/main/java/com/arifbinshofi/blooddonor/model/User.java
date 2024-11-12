package com.arifbinshofi.blooddonor.model;

public class User {

    private String id, uid, name, mobile, email, image;

    public User(String id, String uid, String name, String mobile, String email, String image) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.image = image;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
