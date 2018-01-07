package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolkill.superrecyclerview.annotations.BindField;
import com.nicolkill.superrecyclerview.annotations.LayoutResource;
import com.ramzi.inventoryapp.R;

/**
 * Created by Ramzi on 01-Dec-17.
 */
@LayoutResource(R.layout.product_frame)
@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    private int productId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private long price;
    private int unit;
    private int Quantity;

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets product id.
     *
     * @param productId the product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @BindField(id = R.id.pName)
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public long getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(long price) {
        this.price = price;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(int unit) {
        this.unit = unit;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    /**
     * Pric string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pPrice)
    public String Pric() {
        return price + " $";
    }

    /**
     * Uni string.
     *
     * @return the string
     */
    @JsonIgnore
    @BindField(id = R.id.pUnit)
    public String Uni() {
        return unit + " unit";
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", Quantity=" + Quantity +
                '}';
    }
}
