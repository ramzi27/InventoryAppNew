package com.ramzi.inventoryapp;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.networking.RestService;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Maybe;
import okhttp3.ResponseBody;

/**
 * Tests that the parcelable interface is implemented correctly.
 */

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("ALL")
public class ExampleUnitTest {

    public void s() {
        Customer customer = new Customer();
        customer.setPhone("845120");
        customer.setAddress("ramallah");
        customer.setName("samuel tannous");
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        Maybe<ResponseBody> responseBodyMaybe = RestService.getBackupService().backupCustomers(customers);
        try {
            System.out.println(responseBodyMaybe.blockingGet().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}