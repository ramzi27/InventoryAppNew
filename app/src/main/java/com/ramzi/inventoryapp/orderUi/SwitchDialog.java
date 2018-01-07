package com.ramzi.inventoryapp.orderUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.customerUi.CustomerFragment;
import com.ramzi.inventoryapp.util.Extras;

/**
 * Created by user on 12/31/2017.
 */
public class SwitchDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CustomerFragment customerFragment = new CustomerFragment();
        Bundle bundle = getArguments();
        bundle.putString(Extras.mode, Extras.changeOrder);
        customerFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragmentContainer, customerFragment);
        fragmentTransaction.commit();
    }
}
