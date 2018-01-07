package com.ramzi.inventoryapp.paymentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.entity.Payment;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 12/31/2017.
 */
public class PayDialog extends DialogFragment {
    /**
     * The Date picker.
     */
    @BindView(R.id.datePicker)
    DatePicker datePicker;
    /**
     * The Amount.
     */
    @BindView(R.id.amount)
    EditText amount;
    private Payment payment;
    private Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_payment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       
    }
}
