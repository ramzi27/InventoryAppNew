package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Customer;

import java.util.List;

import io.reactivex.Flowable;

/**
 */

@Dao
public interface CustomerDA {
    @Insert
    void save(Customer customer);

    @Delete
    void delete(Customer customer);


    @Query("select * from customer")
    Flowable<List<Customer>> getAllCustomer();

    @Query("delete from customer")
    void deleteTable();

    @Query("select * from customer where id=:i")
    Customer selectCustomer(int i);

    @Insert
    void saveAll(List<Customer> customers);

}
