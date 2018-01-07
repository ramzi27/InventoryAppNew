package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.OrderDetails;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Order details da.
 */
@Dao
public interface OrderDetailsDA {

    /**
     * Get order details by order flowable.
     *
     * @param orderID the order id
     * @return the flowable
     */
    @Query("select * from orderDetails where orderID=:orderID")
    Flowable<List<OrderDetails>>getOrderDetailsByOrder(int orderID);

    /**
     * Delete order details.
     *
     * @param orderDetails the order details
     */
    @Delete
    void deleteOrderDetails(OrderDetails orderDetails);

    /**
     * Save.
     *
     * @param orderDetails the order details
     */
    @Insert
    void save(OrderDetails orderDetails);

    /**
     * Get all order details flowable.
     *
     * @return the flowable
     */
    @Query("select * from orderDetails")
    Flowable<List<OrderDetails>>getAllOrderDetails();

    /**
     * Delete table.
     */
    @Query("delete from orderDetails")
    void deleteTable();

    /**
     * Save all.
     *
     * @param orderDetails the order details
     */
    @Insert
    void saveAll(List<OrderDetails> orderDetails);
}
