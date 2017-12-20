package com.ramzi.inventoryapp.orderUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.util.Extras;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 12/20/2017.
 */
//show orders for selected customer
    //passed by bundle
public class OrderActivity extends AppCompatActivity {
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.noContent)
    TextView no;
    private SuperRecyclerAdapter<Order>superRecyclerAdapter;
    private ArrayList<Order> orders=new ArrayList<>();
    private Customer customer;
    @BindView(R.id.add)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            customer= (Customer) bundle.getSerializable(Extras.customer);
            floatingActionButton.setOnClickListener(view -> {
                Intent intent=new Intent(this,AddOrder.class);
                intent.putExtras(bundle);
                startActivity(intent);

            });
        }
        setTitle(customer.getName()+" Orders");
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        superRecyclerAdapter = new SuperRecyclerAdapter(list);
       if (bundle!=null)
           getOrders();
    }

    public void getOrders() {
        Flowable<List<Order>> orderFlowable= DB.getDB(this).getOrderDA().getCustomerOrders(customer.getId());
        orderFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders1 -> {
                    if (orders1.size() > 0) {
                        orders.clear();
                        orders.addAll(orders1);
                        superRecyclerAdapter.setElements(orders);
                        superRecyclerAdapter.notifyDataSetChanged();
                        list.setVisibility(View.VISIBLE);
                        no.setVisibility(View.INVISIBLE);
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                });
    }
}
