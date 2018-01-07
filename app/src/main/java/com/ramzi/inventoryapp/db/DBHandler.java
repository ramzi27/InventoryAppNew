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
 * The type Db handler.
 */
@Database(exportSchema = false, version = 2, entities = {Customer.class, Payment.class, Product.class, Order.class, OrderDetails.class})
@TypeConverters({DateConvertor.class})
public abstract class DBHandler extends RoomDatabase {

    /**
     * Gets customer da.
     *
     * @return the customer da
     */
    abstract public CustomerDA getCustomerDA();

    /**
     * Gets payment da.
     *
     * @return the payment da
     */
    abstract public PaymentDA getPaymentDA();

    /**
     * Gets product da.
     *
     * @return the product da
     */
    abstract public ProductDA getProductDA();

    /**
     * Gets order da.
     *
     * @return the order da
     */
    abstract public OrderDA getOrderDA();

    /**
     * Gets order details da.
     *
     * @return the order details da
     */
    abstract public OrderDetailsDA getOrderDetailsDA();
}
