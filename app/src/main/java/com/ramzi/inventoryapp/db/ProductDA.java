package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ramzi.inventoryapp.entity.Product;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Product da.
 */
@Dao
public interface ProductDA {
    /**
     * Save.
     *
     * @param p the p
     */
    @Insert
    void save(Product p);

    /**
     * Update.
     *
     * @param p the p
     */
    @Update
    void update(Product... p);

    /**
     * Gets all products.
     *
     * @return the all products
     */
    @Query("select * from product")
    Flowable<List<Product>> getAllProducts();

    /**
     * Select product flowable.
     *
     * @param s the s
     * @return the flowable
     */
    @Query("select * from product where name=:s")
    Flowable<List<Product>> selectProduct(String s);

    /**
     * Delete table.
     */
    @Query("delete from product")
    void deleteTable();

    /**
     * Save all.
     *
     * @param products the products
     */
    @Insert
    void saveAll(List<Product> products);
}