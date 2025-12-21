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
    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;

    public Order() {
    }

    public Order(String id, String userId, String userEmail, String date, double totalPrice, String status, List<CartItem> items, String shippingName, String shippingPhone, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.date = date;
        this.totalPrice = totalPrice;
        this.status = status;
        this.items = items;
        this.shippingName = shippingName;
        this.shippingPhone = shippingPhone;
        this.shippingAddress = shippingAddress;
    }

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
    public String getShippingName() { return shippingName; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }
    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}