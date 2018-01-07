package com.ramzi.inventoryapp.backup;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * The type Restore fragment.
 */
public class RestoreFragment extends android.support.v4.app.Fragment {
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
        View v = inflater.inflate(R.layout.backup_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Restore");

        backUp.setText("Restore Database");
        backUp.setOnClickListener(view1 -> backUp());
    }

    private void backUp() {
//        if(!Utils.checkWifi(getContext())){
//            Utils.showSnackbar(backProgress,"Check Internet Connection");
//            return;
//        }
        final int[] x = new int[5];
//        getContext().deleteDatabase("myDataBase");
//        getContext().openOrCreateDatabase("myDataBase", Context.MODE_PRIVATE,null,null);
        progressContainer.setVisibility(View.VISIBLE);
        d1 = RestService.getRestoreService().restoreCustomers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers -> {
                    DB.getDB(getContext()).getCustomerDA().deleteTable();
                    DB.getDB(getContext()).getCustomerDA().saveAll(customers);
                    x[0] = customers.size();
                }, throwable -> {
                    throwable.printStackTrace();
                    Utils.showToast(getContext(), "can't restore");
                });

        d2 = RestService.getRestoreService().restoreProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    DB.getDB(getContext()).getProductDA().deleteTable();
                    DB.getDB(getContext()).getProductDA().saveAll(products);
                    x[1] = products.size();
                }, throwable -> {
                    throwable.printStackTrace();
                    Utils.showToast(getContext(), "can't restore");
                });

        d3 = RestService.getRestoreService().restoreOrders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders -> {
                    DB.getDB(getContext()).getOrderDA().deleteTable();
                    DB.getDB(getContext()).getOrderDA().saveAll(orders);
                    x[2] = orders.size();
                }, throwable -> {
                    throwable.printStackTrace();
                    Utils.showToast(getContext(), "can't restore");
                });

        d4 = RestService.getRestoreService().restoreOrderDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetails -> {
                    DB.getDB(getContext()).getOrderDetailsDA().deleteTable();
                    DB.getDB(getContext()).getOrderDetailsDA().saveAll(orderDetails);
                    x[3] = orderDetails.size();
                }, throwable -> {
                    throwable.printStackTrace();
                    Utils.showToast(getContext(), "can't restore");
                });

        d5 = RestService.getRestoreService().restorePayments().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {
                    DB.getDB(getContext()).getPaymentDA().deleteTable();
                    DB.getDB(getContext()).getPaymentDA().saveAll(payments);
                    x[4] = payments.size();
                    backProgress.setVisibility(View.INVISIBLE);
                    String s = "restored customer: " + x[0] + "\n" +
                            "restored products: " + x[1] + "\n" +
                            "restored orders: " + x[2] + "\n" +
                            "restored order details: " + x[3] + "\n" +
                            "restored payment: " + x[4];
                    backResult.setText(s);
                    TransitionDrawable transitionDrawable = (TransitionDrawable) image.getDrawable();
                    transitionDrawable.startTransition(3000);
                }, throwable -> {
                    throwable.printStackTrace();
                    Utils.showToast(getContext(), "can't restore");
                });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (d1 != null && d2 != null && d3 != null && d4 != null && d5 != null) {
            d1.dispose();
            d2.dispose();
            d3.dispose();
            d4.dispose();
            d5.dispose();
        }
    }
}
