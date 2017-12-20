package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;

/**
 * Created by Ramzi on 28-Nov-17.
 */

@Database(exportSchema = false, version = 2, entities = {Customer.class, Payment.class, Product.class, Order.class, OrderDetails.class})
public abstract class DBHandler extends RoomDatabase {

    abstract public CustomerDA getCustomerDA();

    abstract public PaymentDA getPaymentDA();

    abstract public ProductDA getProductDA();
    abstract public OrderDA getOrderDA();

}
