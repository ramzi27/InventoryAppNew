package com.ramzi.inventoryapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.networking.RestService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void s() {
        Customer customer = new Customer();
        customer.setPhone("845120");
        customer.setAddress("ramallah");
        customer.setName("samuel tannous");
        Customer customer1 = new Customer();
        customer1.setPhone("845120");
        customer1.setAddress("ramallah");
        customer1.setName("samuel tannous");
        Context c = InstrumentationRegistry.getContext();
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer1);
        Maybe<ResponseBody> responseBodyMaybe = RestService.getBackupService().backupCustomers(customers);
        responseBodyMaybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    Log.i(c.getPackageName(), responseBody.string());
                });

    }
}
