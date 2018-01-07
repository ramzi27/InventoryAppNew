package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

import java.util.Date;

/**
 * Created by Ramzi on 28-Nov-17.
 */
@LayoutResource(value = R.layout.payment_frame)
@Entity(tableName = "payment", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId", onDelete = ForeignKey.CASCADE)})
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payId")
    private int id;
    private long amount;
    @ColumnInfo(name = "customerId")
    private int customerId;
    private Date date;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pID)
    public String i() {
        return id + "";
    }

    /**
     * Money string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pAmount)
    public String money() {
        return amount + " $";
    }

    /**
     * Date string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pDueDate)
    public String date() {
        return date.toString();
    }
}
