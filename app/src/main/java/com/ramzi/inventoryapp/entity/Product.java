package com.ramzi.inventoryapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @BindField(id = R.id.pName)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @BindField(id = R.id.pPrice)
    public String getPrices() {
        return price + " $";
    }

    @BindField(id = R.id.pUnit)
    public String getUnits() {
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
