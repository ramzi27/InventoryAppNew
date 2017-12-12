package com.ramzi.inventoryapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ramzi.inventoryapp.db.CustomerDA;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void saveCustomer() {
        Customer customer = new Customer();
        customer.setPhone("845120");
        customer.setAddress("ramallah");
        customer.setName("samuel tannous");
        Context c = InstrumentationRegistry.getContext();
        CustomerDA customerDA = DB.getDB(c).getCustomerDA();
        customerDA.save(customer);
    }
}
