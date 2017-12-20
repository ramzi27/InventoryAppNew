package com.ramzi.inventoryapp.orderUi;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.util.Extras;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 12/20/2017.
 */

public class AddOrder extends AppCompatActivity {
    private OrderDetails orderDetails=new OrderDetails();
    @BindView(R.id.proName)
    TextView proId;
    @BindView(R.id.finalPrice)
    EditText finalPrice;
    @BindView(R.id.quantity)
    EditText quantity;
    @BindView(R.id.addProduct)
    FloatingActionButton floatingActionButton;
    private Order order;
    private Product product;

    //takes bundle of order
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);
        ButterKnife.bind(this);
        setTitle("Add Order Details");
        if(getIntent().getExtras()!=null){
            order= (Order) getIntent().getExtras().getSerializable(Extras.order);
        }
        floatingActionButton.setOnClickListener(view -> {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.save:
                saveOrder();
        }
        return true;
    }

    private void saveOrder() {
    }
}
