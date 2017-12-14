package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.io.Serializable;

/**
 * Created by user on 12/12/2017.
 */
@Entity(tableName = "order", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId")})

public class Order implements Serializable {
    private int orderId;
    private long date;
    private long dueDate;
    private int customerId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
