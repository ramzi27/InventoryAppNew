package com.ramzi.inventoryapp.backup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.ramzi.inventoryapp.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class BackupFragment extends Fragment {
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
       View view=inflater.inflate(R.layout.backup_layout,container,false);
       ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("BackUp");
        backUp.setOnClickListener(view1 -> backUp());
    }

    private void backUp() {
        progressContainer.setVisibility(View.VISIBLE);
        DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    RestService.getBackupService().backupCustomers(customers).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "customer backup success!");
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        DB.getDB(getContext()).getProductDA().getAllProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    Log.i(getContext().getPackageName(), products.toString());
                    backResult.setText("backing up products .....");
                    RestService.getBackupService().backupProducts(products).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "product backup success!");
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        DB.getDB(getContext()).getOrderDA().getAllOrders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders -> {
                    backResult.setText("backing up orders .....");
                    RestService.getBackupService().backupOrders(orders).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "order backup success!");

                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        DB.getDB(getContext()).getOrderDetailsDA().getAllOrderDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetails -> {
                    backResult.setText("backing up order details .....");
                    RestService.getBackupService().backupOrderDetails(orderDetails).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "order details backup success!");

                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        DB.getDB(getContext()).getPaymentDA().getAllPayments().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {
                    backResult.setText("backing up payments .....");
                    RestService.getBackupService().backupPayments(payments).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "payment backup success!");
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                    progressContainer.setVisibility(View.INVISIBLE);
                    dataBaseResult.setText("Done");
                });

    }
}
