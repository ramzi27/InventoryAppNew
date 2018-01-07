package com.ramzi.inventoryapp.paymentUi;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * The type Notification reciever.
 */
public class NotificationReciever extends BroadcastReceiver {

    private Context c;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.

        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);
    }

}
