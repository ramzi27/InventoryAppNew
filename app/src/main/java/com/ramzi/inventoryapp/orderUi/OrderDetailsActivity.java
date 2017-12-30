package com.ramzi.inventoryapp.orderUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.util.Extras;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsActivity extends AppCompatActivity {
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.noContent)
    TextView no;
    @BindView(R.id.add)
    FloatingActionButton floatingActionButton;
    private SuperRecyclerAdapter<OrderDetails> superRecyclerAdapter;
    private ArrayList<OrderDetails> orderDetails = new ArrayList<>();
    private Customer customer;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        floatingActionButton.startAnimation(animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        superRecyclerAdapter = new SuperRecyclerAdapter(list);
        superRecyclerAdapter.setOnLongClickListener((view, position, element) -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.inflate(R.menu.delete_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.delete)
                    deleteOrderDetail(element);
                return true;
            });
            popupMenu.show();
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            customer = (Customer) bundle.getSerializable(Extras.customer);
            order = (Order) bundle.getSerializable(Extras.order);
            setTitle(customer.getName() + " order products");
            floatingActionButton.setOnClickListener(view -> {
                Intent intent = new Intent(this, AddOrderDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            getOrderDetails();
        }
    }

    private void deleteOrderDetail(OrderDetails element) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Delete Order Detail")
                .setMessage("Do You Want To Delete Order Detail ?")
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    DB.getDB(this).getOrderDetailsDA().deleteOrderDetails(element);
                }).show();
    }

    private void getOrderDetails() {
        DB.getDB(this).getOrderDetailsDA().getOrderDetailsByOrder(order.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetails -> {
                    if (orderDetails.size() > 0) {
                        this.orderDetails.clear();
                        this.orderDetails.addAll(orderDetails);
                        superRecyclerAdapter.setElements(this.orderDetails);
                        superRecyclerAdapter.notifyDataSetChanged();
                        list.setVisibility(View.VISIBLE);
                        no.setVisibility(View.INVISIBLE);
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                DB.getDB(this).getOrderDetailsDA().deleteTable();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
