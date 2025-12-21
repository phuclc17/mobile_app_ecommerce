package com.example.proj_ecom_mobile.model;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String email;
    private String name;
    private String role;
    private String phone;
    private String address;

    public User() {
    }

    public User(String uid, String email, String name, String role, String phone, String address) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}