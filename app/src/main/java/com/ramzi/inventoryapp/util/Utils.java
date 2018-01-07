package com.ramzi.inventoryapp.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ramzi on 24-Nov-17.
 */
public final class Utils {
    private static ArrayList<EditText> editTexts = new ArrayList<>();

    /**
     * Show snackbar.
     *
     * @param v the v
     * @param s the s
     */
    public static void showSnackbar(View v, String s) {
        Snackbar snackbar = Snackbar.make(v, s, Snackbar.LENGTH_LONG);
        snackbar.setAction("ok", view -> snackbar.dismiss());
        snackbar.show();
    }

    /**
     * Is valid boolean.
     *
     * @param editTexts the edit texts
     * @return the boolean
     */
    public static boolean isValid(EditText... editTexts) { //return invalid edittext
        boolean valid = true;
        for (EditText t : editTexts) {
            Utils.editTexts.add(t);
            if (TextUtils.isEmpty(t.getText().toString()) || t.getText() == null) {
                t.setError("can't be empty");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Show toast.
     *
     * @param c the c
     * @param s the s
     */
    public static void showToast(Context c, String s) {
        Toast toast = Toast.makeText(c, s, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Clear texts.
     */
    public static void clearTexts() {
        for (EditText t : editTexts) {
            t.setText("");
        }
        editTexts.clear();
    }

    /**
     * From json t.
     *
     * @param <T> the type parameter
     * @param s   the s
     * @param c   the c
     * @return the t
     */
    public static <T> T fromJson(String s, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(s, c);
    }

    /**
     * To json string.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return the string
     */
    public static <T> String toJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    /**
     * Check wifi boolean.
     *
     * @param c the c
     * @return the boolean
     */
    public static boolean checkWifi(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

}
