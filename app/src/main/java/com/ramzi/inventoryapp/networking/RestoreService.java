package com.ramzi.inventoryapp.networking;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/***/

public interface RestoreService {
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
    @GET("restore_orders_details")
    Observable<List<OrderDetails>> restoreOrderDetails();

    @Headers("Content-Type: application/json")
    @GET("restore_payments")
    Observable<List<Payment>> restorePayments();
}
