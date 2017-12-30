package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;

/**
 */

@Database(exportSchema = false, version = 2, entities = {Customer.class, Payment.class, Product.class, Order.class, OrderDetails.class})
@TypeConverters({DateConvertor.class})
public abstract class DBHandler extends RoomDatabase {

    abstract public CustomerDA getCustomerDA();

    abstract public PaymentDA getPaymentDA();

    abstract public ProductDA getProductDA();
    abstract public OrderDA getOrderDA();
    abstract public OrderDetailsDA getOrderDetailsDA();
}
