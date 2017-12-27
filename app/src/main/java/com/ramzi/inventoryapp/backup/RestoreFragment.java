package com.ramzi.inventoryapp.backup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.networking.RestService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 12/27/2017.
 */

public class RestoreFragment extends android.support.v4.app.Fragment {
    @BindView(R.id.backUp)
    Button backUp;
    @BindView(R.id.backResult)
    TextView backResult;
    @BindView(R.id.backupProgress)
    ProgressBar backProgress;
    @BindView(R.id.progressContainer)
    LinearLayout progressContainer;
    @BindView(R.id.databaseResult)
    TextView dataBaseResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.backup_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backUp.setText("Restore Database");
        backUp.setOnClickListener(view1 -> backUp());
    }

    private void backUp() {
        final int[] x = new int[5];
        getContext().deleteDatabase("myDataBase");
        progressContainer.setVisibility(View.VISIBLE);
        RestService.getRestoreService().restoreCustomers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
//                    DB.getDB(getContext()).getCustomerDA().deleteTable();
                    DB.getDB(getContext()).getCustomerDA().saveAll(customers);
                    x[0] = customers.size();
                });

        RestService.getRestoreService().restoreProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
//                    DB.getDB(getContext()).getProductDA().deleteTable();
                    DB.getDB(getContext()).getProductDA().saveAll(products);
                    x[1] = products.size();
                });

        RestService.getRestoreService().restoreOrders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders -> {
//                    DB.getDB(getContext()).getOrderDA().deleteTable();
                    DB.getDB(getContext()).getOrderDA().saveAll(orders);
                    x[2] = orders.size();
                });

        RestService.getRestoreService().restoreOrderDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetails -> {
//                    DB.getDB(getContext()).getOrderDetailsDA().deleteTable();
                    DB.getDB(getContext()).getOrderDetailsDA().saveAll(orderDetails);
                    x[3] = orderDetails.size();
                });

        RestService.getRestoreService().restorePayments().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {
//                    DB.getDB(getContext()).getPaymentDA().deleteTable();
                    DB.getDB(getContext()).getPaymentDA().saveAll(payments);
                    x[4] = payments.size();
                    backProgress.setVisibility(View.INVISIBLE);
                    String s = "restored customer: " + x[0] + "\n" +
                            "restored products: " + x[1] + "\n" +
                            "restored orders: " + x[2] + "\n" +
                            "restored order details: " + x[3] + "\n" +
                            "restored payment: " + x[4];
                    backResult.setText(s);
                });


    }
}
