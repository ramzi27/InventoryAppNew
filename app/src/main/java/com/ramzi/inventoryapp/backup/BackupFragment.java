package com.ramzi.inventoryapp.backup;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Backup fragment.
 */
public class BackupFragment extends Fragment {
    /**
     * The Back up.
     */
    @BindView(R.id.backUp)
    Button backUp;
    /**
     * The Back result.
     */
    @BindView(R.id.backResult)
    TextView backResult;
    /**
     * The Back progress.
     */
    @BindView(R.id.backupProgress)
    ProgressBar backProgress;
    /**
     * The Progress container.
     */
    @BindView(R.id.progressContainer)
    LinearLayout progressContainer;
    /**
     * The Data base result.
     */
    @BindView(R.id.databaseResult)
    TextView dataBaseResult;
    /**
     * The Image.
     */
    @BindView(R.id.bImage)
    ImageView image;

    private Disposable d1, d2, d3, d4, d5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.backup_layout,container,false);
       ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (d1 != null && d2 != null && d3 != null && d4 != null && d5 != null) {
            d1.dispose();
            d2.dispose();
            d3.dispose();
            d5.dispose();
            d4.dispose();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("BackUp");
        backUp.setOnClickListener(view1 -> backUp());
    }

    private void backUp() {
//        if(!Utils.checkWifi(getContext())) {
//            Utils.showSnackbar(backResult, "No Internet Connection");
//            return;
//        }
        progressContainer.setVisibility(View.VISIBLE);
        d1 = DB.getDB(getContext()).getCustomerDA().getAllCustomer().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    RestService.getBackupService().backupCustomers(customers).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "customer backup success!");
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        d2 = DB.getDB(getContext()).getProductDA().getAllProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    backResult.setText("backing up products .....");
                    RestService.getBackupService().backupProducts(products).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "product backup success!");
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        d3 = DB.getDB(getContext()).getOrderDA().getAllOrders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders -> {
                    backResult.setText("backing up orders .....");
                    RestService.getBackupService().backupOrders(orders).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "order backup success!");

                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        d4 = DB.getDB(getContext()).getOrderDetailsDA().getAllOrderDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetails -> {
                    backResult.setText("backing up order details .....");
                    RestService.getBackupService().backupOrderDetails(orderDetails).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "order details backup success!");

                    }, throwable -> Utils.showToast(getContext(), "can't backup"));
                });
        d5 = DB.getDB(getContext()).getPaymentDA().getAllPayments().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {
                    backResult.setText("backing up payments .....");
                    RestService.getBackupService().backupPayments(payments).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(responseBody -> {
                        Utils.showToast(getContext(), "payment backup success!");
                        progressContainer.setVisibility(View.INVISIBLE);
                        dataBaseResult.setText("Done");
                        TransitionDrawable transitionDrawable = (TransitionDrawable) image.getDrawable();
                        transitionDrawable.startTransition(3000);
                    }, throwable -> Utils.showToast(getContext(), "can't backup"));

                });

    }
}
