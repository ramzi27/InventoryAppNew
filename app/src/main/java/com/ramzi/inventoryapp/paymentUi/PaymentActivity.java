package com.ramzi.inventoryapp.paymentUi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.util.Extras;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Payment activity.
 */
public class PaymentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * The List.
     */
    @BindView(R.id.list)
    RecyclerView list;
    /**
     * The No.
     */
    @BindView(R.id.noContent)
    TextView no;
    /**
     * The Button.
     */
    @BindView(R.id.add)
    FloatingActionButton button;
    /**
     * The Refresh layout.
     */
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private SuperRecyclerAdapter<Payment> superRecyclerAdapter;
    private ArrayList<Payment> payments = new ArrayList<>();
    private Customer customer;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            customer = (Customer) getIntent().getExtras().getSerializable(Extras.customer);
        }
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(true);
        refreshLayout.setColorSchemeResources(R.color.refresh_toolbar_color, R.color.refresh_color);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(customer.getName() + " payments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        button.startAnimation(animation);
        button.setOnClickListener(view -> {
            PayDialog payDialog = new PayDialog();
            payDialog.show(getSupportFragmentManager(), "h");

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        superRecyclerAdapter = new SuperRecyclerAdapter(list);
        superRecyclerAdapter.setOnLongClickListener((view, position, element) -> {
            buildDialog(element);
        });
        getPaymentsFromDB();
    }

    private void buildDialog(Payment element) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Payment")
                .setMessage("Do You Want To Delete Payment ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> DB.getDB(this).getPaymentDA().delete(element))
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }

    private void getPaymentsFromDB() {
        Flowable<List<Payment>> paymentFlowable = DB.getDB(this).getPaymentDA().getCustomerPayments(customer.getId());
        disposable = paymentFlowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> {
                    if (payments.size() > 0) {
                        this.payments.clear();
                        this.payments.addAll(payments);
                        superRecyclerAdapter.setElements(payments);
                        superRecyclerAdapter.notifyDataSetChanged();
                        list.setVisibility(View.VISIBLE);
                        no.setVisibility(View.INVISIBLE);
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                    refreshLayout.setRefreshing(false);
                });
    }

    @Override
    public void onRefresh() {
        superRecyclerAdapter.clearData();
        getPaymentsFromDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}
