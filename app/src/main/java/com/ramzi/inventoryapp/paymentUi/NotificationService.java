package com.ramzi.inventoryapp.paymentUi;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Payment;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Notification service.
 */
public class NotificationService extends Service {
    private Disposable d;
    private boolean isRunning;
    private Context context;
    private BackgroundThread backgroundThread;

    /**
     * Instantiates a new Notification service.
     */
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new BackgroundThread(context);
    }


    @Override
    public void onDestroy() {
        this.isRunning = false;
        if (d != null)
            d.dispose();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    private class BackgroundThread extends Thread {
        private Context c;

        /**
         * Instantiates a new Background thread.
         *
         * @param context the context
         */
        public BackgroundThread(Context context) {
            c = context;
        }

        @Override
        public void run() {//       c=context;
            d = DB.getDB(c).getPaymentDA().getAllPayments().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(payments -> {

                        for (Payment p : payments) {
                            if (p.getDate().before(new Date()))
                                showNotification(p);
                        }
                    });


        }

        private void showNotification(Payment p) {
            Customer customer = DB.getDB(this.c).getCustomerDA().selectCustomer(p.getCustomerId());

            String CHANNEL_ID = "myChannel";
            Log.i(c.getPackageName(), "notify");
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(c, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notify)
                            .setContentTitle("Payment Out Of Date")
                            .setAutoCancel(true)
                            .setContentText(customer.getName() + " payment was outdated !");

            NotificationManager mNotificationManager =
                    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(114, mBuilder.build());
        }
    }
}

