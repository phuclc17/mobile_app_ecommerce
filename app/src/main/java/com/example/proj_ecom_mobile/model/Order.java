package com.example.proj_ecom_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String userId;
    private String userEmail;
    private String date;
    private double totalPrice;
    private String status;
    private List<CartItem> items;

    // --- CÁC TRƯỜNG MỚI THÊM CHO VIỆC GIAO HÀNG ---
    private String recipientName; // Tên người nhận
    private String phoneNumber;   // Số điện thoại
    private String address;       // Địa chỉ giao hàng

    public Order() {
    }

    public Order(String id, String userId, String userEmail, String date, double totalPrice, String status, List<CartItem> items, String recipientName, String phoneNumber, String address) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.date = date;
        this.totalPrice = totalPrice;
        this.status = status;
        this.items = items;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // --- GETTER & SETTER ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}