package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 12/12/2017.
 */
@Entity(tableName = "orderTable", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId", onDelete = ForeignKey.CASCADE)})
@LayoutResource(R.layout.order_frame)
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    private Date date;
    private Date dueDate;
    private int customerId;

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
     * Gets due date.
     *
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * D string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id=R.id.oId)
    public String D() {
        return orderId + "";
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Date string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id=R.id.oDate)
    public String Date() {
        return dueDate.toString();
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
}
