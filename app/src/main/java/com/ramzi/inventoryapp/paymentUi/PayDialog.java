package com.ramzi.inventoryapp.paymentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzi.inventoryapp.R;

/**
 * Created by user on 12/31/2017.
 */

public class PayDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_payment, container, false);
        return v;
    }
}
