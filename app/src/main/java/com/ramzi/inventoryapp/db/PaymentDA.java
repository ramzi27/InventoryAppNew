package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ramzi.inventoryapp.entity.Payment;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Ramzi on 28-Nov-17.
 */
@Dao
public interface PaymentDA {
    @Insert
    void save(Payment payment);

    @Delete
    void delete(Payment payment);

    @Delete
    void delete(List<Payment> payments);

    @Insert
    void save(List<Payment> payments);

    @Query("select * from payment where customerId=:cusID")
    Flowable<List<Payment>> getCustomerPayments(int cusID);


}
