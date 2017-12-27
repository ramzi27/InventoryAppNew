package com.ramzi.inventoryapp.paymentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.customerUi.CustomerFragment;
import com.ramzi.inventoryapp.util.Extras;

import butterknife.ButterKnife;

/**
 * Created by user on 12/27/2017.
 */

public class PaymentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_dialog, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Extras.mode, Extras.addPayment);
        CustomerFragment customerFragment = new CustomerFragment();
        customerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, customerFragment);
        fragmentTransaction.commit();
    }
}
