package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Ramzi on 28-Nov-17.
 */

@Entity(tableName = "payment", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId")})
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payId")
    private int id;
    private long amount;
    @ColumnInfo(name = "customerId")
    private int customerId;
    private Date date;
    private int voidInd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVoidInd() {
        return voidInd;
    }

    public void setVoidInd(int voidInd) {
        this.voidInd = voidInd;
    }
}
