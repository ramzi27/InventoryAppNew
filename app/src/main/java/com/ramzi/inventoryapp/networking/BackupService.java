package com.ramzi.inventoryapp.networking;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by user on 12/24/2017.
 */

public interface BackupService {
    @Headers("Content-Type: application/json")
    @POST("backup_customers")
    void backupCustomers(@Body List<Customer>customers);


    @Headers("Content-Type: application/json")
    @POST("backup_products")
    void backupProducts(@Body List<Product>products);

    @Headers("Content-Type: application/json")
    @POST("backup_orders")
    void backupOrders(@Body List<Order>orders);

    @Headers("Content-Type: application/json")
    @POST("backup_order_details")
    void backupOrderDetails(@Body List<OrderDetails>orderDetails);

    @Headers("Content-Type: application/json")
    @POST("backup_payments")
    void backupPayments(@Body List<Payment>payments);


    @Headers("Content-Type: application/json")
    @GET("restore_customers")
    Observable<List<Customer>> restoreCustomers();

    @Headers("Content-Type: application/json")
    @GET("restore_products")
    Observable<List<Product>> restoreProducts();

    @Headers("Content-Type: application/json")
    @GET("restore_orders")
    Observable<List<Order>> restoreOrders();

    @Headers("Content-Type: application/json")
    @GET("restore_order_details")
    Observable<List<OrderDetails>> restoreOrderDetails();

    @Headers("Content-Type: application/json")
    @GET("restore_payments")
    Observable<List<Payment>> restorePayments();

}
