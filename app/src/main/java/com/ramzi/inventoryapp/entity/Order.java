package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by user on 12/12/2017.
 */
@Entity(tableName = "orderTable", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "customerId")})
@LayoutResource(R.layout.order_frame)
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    private long date;
    private long dueDate;
    private int customerId;

    @BindField(id=R.id.oId)
    public String getID(){return orderId+"";}

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

    @BindField(id=R.id.oDate)
    public String getDa(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.getTime().toString();

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
