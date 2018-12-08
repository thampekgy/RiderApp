package com.example.windows10.riderapp.Model;

import java.util.List;

public class Delivered {

    String orderId;
    String prodectName;
    String qty;

    public Delivered() {
    }

    public Delivered(String orderId, String prodectName, String qty) {
        this.orderId = orderId;
        this.prodectName = prodectName;
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdectName() {
        return prodectName;
    }

    public void setProdectName(String prodectName) {
        this.prodectName = prodectName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
