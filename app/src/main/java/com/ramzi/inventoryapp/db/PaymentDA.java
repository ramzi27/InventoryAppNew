package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Payment;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Payment da.
 */
@Dao
public interface PaymentDA {

    /**
     * Save.
     *
     * @param payment the payment
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Payment payment);


    /**
     * Delete.
     *
     * @param payment the payment
     */
    @Delete
    void delete(Payment payment);

    /**
     * Delete.
     *
     * @param payments the payments
     */
    @Delete
    void delete(List<Payment> payments);

    /**
     * Save.
     *
     * @param payments the payments
     */
    @Insert
    void save(List<Payment> payments);

    /**
     * Gets customer payments.
     *
     * @param cusID the cus id
     * @return the customer payments
     */
    @Query("select * from payment where customerId=:cusID")
    Flowable<List<Payment>> getCustomerPayments(int cusID);

    /**
     * Gets all payments.
     *
     * @return the all payments
     */
    @Query("select * from payment")
    Flowable<List<Payment>> getAllPayments();

    /**
     * Save all.
     *
     * @param payments the payments
     */
    @Insert
    void saveAll(List<Payment> payments);


    /**
     * Delete table.
     */
    @Query("delete from payment")
    void deleteTable();

}
