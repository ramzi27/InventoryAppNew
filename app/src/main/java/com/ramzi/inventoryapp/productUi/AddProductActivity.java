package com.ramzi.inventoryapp.productUi;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.util.Extras;
import com.ramzi.inventoryapp.util.Utils;

public class AddProductActivity extends AppCompatActivity {

    private CoordinatorLayout productContainer;
    private EditText nameP;
    private EditText priceP;
    private EditText unitP;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        setTitle("Products");
        initView();
        if (getIntent().getExtras().getString(Extras.mode).matches(Extras.updateProduct))
            showProduct();
    }

    private void showProduct() {
        product = Utils.fromJson(getIntent().getExtras().getString(Extras.product), Product.class);
        nameP.setText(product.getName());
        unitP.setText(product.getUnit() + "");
        priceP.setText(product.getPrice() + "");
    }

    private void initView() {
        productContainer = findViewById(R.id.productContainer);
        nameP = findViewById(R.id.nameP);
        priceP = findViewById(R.id.priceP);
        unitP = findViewById(R.id.unitP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
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
                saveProduct();
        }
        return true;
    }

    private void saveProduct() {
        String n, p, u;
        n = nameP.getText().toString();
        p = priceP.getText().toString();
        u = unitP.getText().toString();
        if (Utils.isValid(nameP, priceP, unitP)) {

            Product pr = new Product();
            pr.setName(n);
            pr.setPrice(Long.valueOf(p));
            pr.setUnit(Integer.valueOf(u));
            if (getIntent().getExtras().getString(Extras.mode).matches(Extras.updateProduct)) {
                pr.setProductId(product.getProductId());
                DB.getDB(getApplicationContext()).getProductDA().update(pr);
                Utils.showSnackbar(productContainer, "updated");
            } else {
                DB.getDB(getApplicationContext()).getProductDA().save(pr);
                Utils.showSnackbar(productContainer, "saved");
            }
        }
        Utils.clearTexts();
    }
}
