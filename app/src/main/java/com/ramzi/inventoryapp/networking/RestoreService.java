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

/**
 * The interface Restore service.
 */
public interface RestoreService {
    /**
     * Restore customers observable.
     *
     * @return the observable
     */
    @Headers("Content-Type: application/json")
    @GET("restore_customers")
    Observable<List<Customer>> restoreCustomers();

    /**
     * Restore products observable.
     *
     * @return the observable
     */
    @Headers("Content-Type: application/json")
    @GET("restore_products")
    Observable<List<Product>> restoreProducts();

    /**
     * Restore orders observable.
     *
     * @return the observable
     */
    @Headers("Content-Type: application/json")
    @GET("restore_orders")
    Observable<List<Order>> restoreOrders();

    /**
     * Restore order details observable.
     *
     * @return the observable
     */
    @Headers("Content-Type: application/json")
    @GET("restore_orders_details")
    Observable<List<OrderDetails>> restoreOrderDetails();

    /**
     * Restore payments observable.
     *
     * @return the observable
     */
    @Headers("Content-Type: application/json")
    @GET("restore_payments")
    Observable<List<Payment>> restorePayments();
}
