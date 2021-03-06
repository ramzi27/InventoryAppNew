package com.ramzi.inventoryapp.orderUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Order;
import com.ramzi.inventoryapp.entity.OrderDetails;
import com.ramzi.inventoryapp.entity.Payment;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.productUi.ProductDialog;
import com.ramzi.inventoryapp.util.Extras;
import com.ramzi.inventoryapp.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Add order details activity.
 */
public class AddOrderDetailsActivity extends AppCompatActivity implements ProductDialog.OnProductSelected {
    /**
     * The Pro id.
     */
    @BindView(R.id.proName)
    TextView proId;
    /**
     * The Final price.
     */
    @BindView(R.id.finalPrice)
    TextView finalPrice;
    /**
     * The Quantity.
     */
    @BindView(R.id.quantity)
    EditText quantity;
    /**
     * The Floating action button.
     */
    @BindView(R.id.addProduct)
    FloatingActionButton floatingActionButton;
    private Order order;
    private Product product;
    private ProductDialog productDialog;
    private Disposable disposable;
    //takes bundle of order
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_details);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        floatingActionButton.startAnimation(animation);
        setTitle("Add Order Details");
        if (getIntent().getExtras() != null) {
            order = (Order) getIntent().getExtras().getSerializable(Extras.order);
        }
        floatingActionButton.setOnClickListener(view -> {
            productDialog = new ProductDialog();
            productDialog.setOnProductSelected(this);
            productDialog.show(getSupportFragmentManager(), "hi");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
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
        if (Utils.isValid(quantity)) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setFinalPrice(Integer.valueOf(finalPrice.getText().toString()));
            orderDetails.setOrderID(order.getOrderId());
            orderDetails.setQuantity(Integer.valueOf(quantity.getText().toString()));
            orderDetails.setProductID(product.getProductId());
            DB.getDB(this).getOrderDetailsDA().save(orderDetails);
            Utils.showSnackbar(proId, "saved");
            Utils.clearTexts();
            finalPrice.setText("");
            quantity.setText("");
            proId.setText("");
            //update total price
            disposable = DB.getDB(this).getOrderDetailsDA().getOrderDetailsByOrder(order.getOrderId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderDetails1 -> {
                        int total = 0;
                        for (OrderDetails o : orderDetails1) {
                            total += (o.getFinalPrice()) * (o.getQuantity());
                        }
                        Payment payment = new Payment();
                        payment.setAmount(total);
                        payment.setCustomerId(order.getCustomerId());
                        payment.setDate(order.getDueDate());
                        DB.getDB(this).getPaymentDA().save(payment);
                    });

        } else {
            Utils.showSnackbar(proId, "select product first!");
        }
    }

    @Override
    public void onSelect(Product product) {
        this.product = product;
        productDialog.getDialog().cancel();
        proId.setText(product.getName());
        finalPrice.setText(product.getPrice() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}
