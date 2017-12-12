package com.ramzi.inventoryapp.util;

import android.content.Context;
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

    public static void showSnackbar(View v, String s) {
        Snackbar snackbar = Snackbar.make(v, s, Snackbar.LENGTH_LONG);
        snackbar.setAction("ok", view -> snackbar.dismiss());
        snackbar.show();
    }

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

    public static void showToast(Context c, String s) {
        Toast toast = Toast.makeText(c, s, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void clearTexts() {
        for (EditText t : editTexts) {
            t.setText("");
        }
        editTexts.clear();
    }

    public static <T> T fromJson(String s, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(s, c);
    }

    public static <T> String toJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }
}
