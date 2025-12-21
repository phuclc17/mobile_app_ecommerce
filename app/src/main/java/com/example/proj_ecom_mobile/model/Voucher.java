package com.example.proj_ecom_mobile.model;

import java.io.Serializable;

public class Voucher implements Serializable {
    private String id;
    private String code;
    private String type;
    private double value;
    private long quantity;

    public Voucher() {
    }

    public Voucher(String id, String code, String type, double value, long quantity) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.value = value;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}