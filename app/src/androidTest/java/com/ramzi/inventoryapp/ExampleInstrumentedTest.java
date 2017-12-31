package com.ramzi.inventoryapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.ramzi.inventoryapp.entity.Order;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void s() {
        Context c = InstrumentationRegistry.getContext();
        Order order = new Order();
        order.setCustomerId(5842);
        order.setDate(new Date());
        order.setDueDate(new Date());
        Gson gson = new Gson();
        Log.i(c.getPackageName(), gson.toJson(order));

    }
}
