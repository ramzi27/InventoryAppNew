package com.ramzi.inventoryapp.orderUi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.util.Extras;
import com.ramzi.inventoryapp.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Order activity.
 */
//show orders for selected customer
    //passed by bundle
public class OrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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
     * The Floating action button.
     */
    @BindView(R.id.add)
    FloatingActionButton floatingActionButton;
    private SuperRecyclerAdapter<Order>superRecyclerAdapter;
    private ArrayList<Order> orders=new ArrayList<>();
    private Customer customer;
    private Date date;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        floatingActionButton.startAnimation(animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            customer= (Customer) bundle.getSerializable(Extras.customer);
            floatingActionButton.setOnClickListener(view -> {
                addOrder();
            });
        }
        setTitle(customer.getName()+" Orders");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        superRecyclerAdapter = new SuperRecyclerAdapter(list);
        superRecyclerAdapter.setOnLongClickListener((view, position, element) -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.inflate(R.menu.orders_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.deleteO)
                    deleteOrder(element);
                else if (menuItem.getItemId() == R.id.switchOrder) {
                    SwitchDialog switchDialog = new SwitchDialog();
                    Bundle bundleq = new Bundle();
                    bundleq.putSerializable(Extras.order, element);
                    switchDialog.setArguments(bundleq);
                    switchDialog.show(getSupportFragmentManager(), "k");
                }
                return true;
            });
        });

        superRecyclerAdapter.setOnClickListener((view, position, element) -> {
            bundle.putSerializable(Extras.order, element);
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

       if (bundle!=null)
           getOrders();
    }

    private void deleteOrder(Order element) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Delete Order")
                .setMessage("Do You Want To Delete Order ?")
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    DB.getDB(this).getOrderDA().delete(element);
                }).show();
    }

    /**
     * add order to db
     */

    private void addOrder() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("select order date");
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "save", (dialogInterface, i) -> {
            this.onDateSet(datePickerDialog.getDatePicker(), datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth());
            saveOrder();
        });
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "cancel", (dialogInterface, i) -> dialogInterface.cancel());
        datePickerDialog.show();
    }

    /**
     * Gets orders.
     */
    public void getOrders() {
        Flowable<List<Order>> orderFlowable= DB.getDB(this).getOrderDA().getCustomerOrders(customer.getId());
        disposable = orderFlowable.subscribeOn(Schedulers.io())
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        date = calendar.getTime();
    }

    private void saveOrder() {
        if (date != null) {
            Order order = new Order();
            order.setCustomerId(customer.getId());
            order.setDate(new Date(System.currentTimeMillis()));
            order.setDueDate(date);
            DB.getDB(this).getOrderDA().save(order);
            Utils.showToast(this, "order saved");
        } else {
            Utils.showToast(this, "order not saved");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAll)
            DB.getDB(this).getOrderDA().deleteTable();
        else if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
