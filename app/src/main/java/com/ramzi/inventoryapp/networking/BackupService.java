package com.ramzi.inventoryapp.networking;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;

import java.util.List;

import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by user on 12/24/2017.
 */

public interface BackupService {
    @Headers("Content-Type: application/json")
    @POST("backup_customers")
    Maybe<ResponseBody> backupCustomers(@Body List<Customer> customers);

    @Headers("Content-Type: application/json")
    @POST("backup_products")
    Maybe<ResponseBody> backupProducts(@Body List<Product> products);

    @Headers("Content-Type: application/json")
    @POST("backup_orders")
    Maybe<ResponseBody> backupOrders(@Body List<Order> orders);

    @Headers("Content-Type: application/json")
    @POST("backup_orders_details")
    Maybe<ResponseBody> backupOrderDetails(@Body List<OrderDetails> orderDetails);

    @Headers("Content-Type: application/json")
    @POST("backup_payments")
    Maybe<ResponseBody> backupPayments(@Body List<Payment> payments);


}
