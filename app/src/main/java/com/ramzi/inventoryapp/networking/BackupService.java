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
 * The interface Backup service.
 */
public interface BackupService {
    /**
     * Backup customers maybe.
     *
     * @param customers the customers
     * @return the maybe
     */
    @Headers("Content-Type: application/json")
    @POST("backup_customers")
    Maybe<ResponseBody> backupCustomers(@Body List<Customer> customers);

    /**
     * Backup products maybe.
     *
     * @param products the products
     * @return the maybe
     */
    @Headers("Content-Type: application/json")
    @POST("backup_products")
    Maybe<ResponseBody> backupProducts(@Body List<Product> products);

    /**
     * Backup orders maybe.
     *
     * @param orders the orders
     * @return the maybe
     */
    @Headers("Content-Type: application/json")
    @POST("backup_orders")
    Maybe<ResponseBody> backupOrders(@Body List<Order> orders);

    /**
     * Backup order details maybe.
     *
     * @param orderDetails the order details
     * @return the maybe
     */
    @Headers("Content-Type: application/json")
    @POST("backup_orders_details")
    Maybe<ResponseBody> backupOrderDetails(@Body List<OrderDetails> orderDetails);

    /**
     * Backup payments maybe.
     *
     * @param payments the payments
     * @return the maybe
     */
    @Headers("Content-Type: application/json")
    @POST("backup_payments")
    Maybe<ResponseBody> backupPayments(@Body List<Payment> payments);


}
