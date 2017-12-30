package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

import java.io.Serializable;

/**
 */
@Entity(tableName = "orderDetails", foreignKeys = {@ForeignKey(entity = Order.class, parentColumns = "orderId", childColumns = "orderID", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Product.class, parentColumns = "productId", childColumns = "productID")
})
@LayoutResource(R.layout.product_frame)
public class OrderDetails implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderID;
    private int productID;
    private int finalPrice;
    private int quantity;

    @JsonIgnore
    @BindField(id = R.id.pName)
    public String getPN() {
        return "ID: " + productID;
    }

    @JsonIgnore
    @BindField(id = R.id.pPrice)
    public String getP() {
        return finalPrice + " $";
    }

    @JsonIgnore
    @BindField(id = R.id.pUnit)
    public String getU() {
        return quantity + " unit";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
