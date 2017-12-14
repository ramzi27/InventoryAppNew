package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Order;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by user on 12/12/2017.
 */

public interface OrderDA {

    @Insert
    void save(Order order);

    @Delete
    void delete(Order order);

    @Query("select * from order")
    Flowable<List<Order>> getAllOrders();


}
