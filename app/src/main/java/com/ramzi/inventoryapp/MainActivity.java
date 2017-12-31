package com.ramzi.inventoryapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ramzi.inventoryapp.backup.BackupFragment;
import com.ramzi.inventoryapp.backup.RestoreFragment;
import com.ramzi.inventoryapp.customerUi.CustomerFragment;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.paymentUi.NotificationReciever;
import com.ramzi.inventoryapp.productUi.ProductFragment;
import com.ramzi.inventoryapp.util.Extras;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private long timeInMillis = 5000;
    private Intent intent;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Disposable d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent alarm = new Intent(this, NotificationReciever.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
//        if (!alarmRunning) {
//            pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
//            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime(), timeInMillis, pendingIntent);
//        }
//        intent=new Intent(this, NotificationService.class);
//        startService(intent);
        checkPayments();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CustomerFragment customerFragment=new CustomerFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Extras.mode,Extras.showCustomers);
        customerFragment.setArguments(bundle);
        changeView(customerFragment);
    }

    private void checkPayments() {
        d = DB.getDB(this).getPaymentDA().getAllPayments().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {

                    for (Payment p : payments) {
                        if (p.getDate().before(new Date()))
                            showNotification(p);
                    }
                });


    }

    private void showNotification(Payment p) {
        Customer customer = DB.getDB(this).getCustomerDA().selectCustomer(p.getCustomerId());

        String CHANNEL_ID = "myChannel";
        Log.i(getPackageName(), "notify");
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notify)
                        .setContentTitle("Payment Out Of Date")
                        .setAutoCancel(true)
                        .setContentText(customer.getName() + " payment was outdated !");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(114, mBuilder.build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();
        navigationView.setCheckedItem(id);
        Bundle bundle=new Bundle();
        switch (id) {
            case R.id.nav_customer:
                CustomerFragment customerFragment=new CustomerFragment();
                bundle.putString(Extras.mode,Extras.showCustomers);
                customerFragment.setArguments(bundle);
                changeView(customerFragment);
                break;

            case R.id.nav_products:
                ProductFragment productFragment=new ProductFragment();
                bundle.putString(Extras.mode,Extras.showProducts);
                productFragment.setArguments(bundle);
                changeView(productFragment);
                break;
            case R.id.nav_backup:
                changeView(new BackupFragment());
                break;
            case R.id.nav_restore:
                changeView(new RestoreFragment());
                break;
            case R.id.nav_payment:
                CustomerFragment customerFragment1 = new CustomerFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Extras.mode, Extras.addPayment);
                customerFragment1.setArguments(bundle1);
                changeView(customerFragment1);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmManager != null && pendingIntent != null)
            alarmManager.cancel(pendingIntent);
    }
}
