package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

import java.io.Serializable;

/**
 * Created by Ramzi on 24-Nov-17.
 */

@Entity(tableName = "customer")
@LayoutResource(R.layout.customer_frame)
public class Customer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private String name, address, phone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @BindField(id = R.id.cName)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @BindField(id = R.id.cAddress)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @BindField(id = R.id.cPhone)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}