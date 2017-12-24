package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by user on 12/20/2017.
 */

@Dao
public interface OrderDetailsDA {

    @Query("select * from orderDetails where orderID=:orderID")
    Flowable<List<OrderDetails>>getOrderDetailsByOrder(int orderID);
    @Delete
    void deleteOrderDetails(OrderDetails orderDetails);
    @Insert
    void save(OrderDetails orderDetails);

    @Query("select * from orderDetails")
    Flowable<List<OrderDetails>>getAllOrderDetails();
}
