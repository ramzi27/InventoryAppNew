package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Order;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Order da.
 */
@Dao
public interface OrderDA {

    /**
     * Save.
     *
     * @param order the order
     */
    @Insert
    void save(Order order);

    /**
     * Delete.
     *
     * @param order the order
     */
    @Delete
    void delete(Order order);

    /**
     * Gets customer orders.
     *
     * @param c the c
     * @return the customer orders
     */
    @Query("select * from orderTable where customerId=:c")
    Flowable<List<Order>> getCustomerOrders(int c);

    /**
     * Gets all orders.
     *
     * @return the all orders
     */
    @Query("select * from orderTable")
    Flowable<List<Order>> getAllOrders();

    /**
     * Delete table.
     */
    @Query("delete from orderTable")
    void deleteTable();

    /**
     * Save all.
     *
     * @param orders the orders
     */
    @Insert
    void saveAll(List<Order> orders);

    /**
     * Switch orders.
     *
     * @param id the id
     * @param O  the o
     */
    @Query("update orderTable set customerId=:id where orderId=:O")
    void switchOrders(int id, int O);

}
