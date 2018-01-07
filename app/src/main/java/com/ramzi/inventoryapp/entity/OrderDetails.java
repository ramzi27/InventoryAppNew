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
 * The type Order details.
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

    /**
     * Pn string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pName)
    public String PN() {
        return "ID: " + productID;
    }

    /**
     * P string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pPrice)
    public String P() {
        return finalPrice + " $";
    }

    /**
     * U string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pUnit)
    public String U() {
        return quantity + " unit";
    }


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
     * Gets order id.
     *
     * @return the order id
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Sets order id.
     *
     * @param orderID the order id
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Sets product id.
     *
     * @param productID the product id
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Gets final price.
     *
     * @return the final price
     */
    public int getFinalPrice() {
        return finalPrice;
    }

    /**
     * Sets final price.
     *
     * @param finalPrice the final price
     */
    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
