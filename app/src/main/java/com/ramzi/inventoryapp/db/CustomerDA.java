package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Customer;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Customer da.
 */
@Dao
public interface CustomerDA {
    /**
     * Save.
     *
     * @param customer the customer
     */
    @Insert
    void save(Customer customer);

    /**
     * Delete.
     *
     * @param customer the customer
     */
    @Delete
    void delete(Customer customer);


    /**
     * Gets all customer.
     *
     * @return the all customer
     */
    @Query("select * from customer")
    Flowable<List<Customer>> getAllCustomer();

    /**
     * Delete table.
     */
    @Query("delete from customer")
    void deleteTable();

    /**
     * Select customer customer.
     *
     * @param i the
     * @return the customer
     */
    @Query("select * from customer where id=:i")
    Customer selectCustomer(int i);

    /**
     * Save all.
     *
     * @param customers the customers
     */
    @Insert
    void saveAll(List<Customer> customers);

}
