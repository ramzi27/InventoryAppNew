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
 */
@Dao
public interface PaymentDA {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Payment payment);


    @Delete
    void delete(Payment payment);

    @Delete
    void delete(List<Payment> payments);

    @Insert
    void save(List<Payment> payments);

    @Query("select * from payment where customerId=:cusID")
    Flowable<List<Payment>> getCustomerPayments(int cusID);

    @Query("select * from payment")
    Flowable<List<Payment>> getAllPayments();

    @Insert
    void saveAll(List<Payment> payments);


    @Query("delete from payment")
    void deleteTable();

}
