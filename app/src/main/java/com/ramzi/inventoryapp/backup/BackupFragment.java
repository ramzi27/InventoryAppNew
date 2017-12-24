package com.ramzi.inventoryapp.backup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 12/24/2017.
 */

public class BackupFragment extends Fragment implements View.OnClickListener {
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
        backUp.setOnClickListener(view1 -> backUp());
    }

    private void backUp() {
        progressContainer.setVisibility(View.VISIBLE);
        DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    backResult.setText("backing up customers .....");
                    RestService.getAPIService().backupCustomers(customers);
                });
        DB.getDB(getContext()).getProductDA().getAllProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    backResult.setText("backing up products .....");
                    RestService.getAPIService().backupProducts(products);
                });
        DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    backResult.setText("backing up customers .....");
                    RestService.getAPIService().backupCustomers(customers);
                });
        DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    backResult.setText("backing up customers .....");
                    RestService.getAPIService().backupCustomers(customers);
                });
        DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    backResult.setText("backing up customers .....");
                    RestService.getAPIService().backupCustomers(customers);
                });
    }
}
