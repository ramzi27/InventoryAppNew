package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ramzi.inventoryapp.entity.Product;

import java.util.List;

import io.reactivex.Flowable;

/**
 */
@Dao
public interface ProductDA {
    @Insert
    void save(Product p);

    @Update
    void update(Product... p);

    @Query("select * from product")
    Flowable<List<Product>> getAllProducts();

    @Query("select * from product where name=:s")
    Flowable<List<Product>> selectProduct(String s);

    @Query("delete from product")
    void deleteTable();

    @Insert
    void saveAll(List<Product> products);
}