package com.ramzi.inventoryapp.customerUi;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.db.DBHandler;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.util.Utils;

/**
 * The type Add customer activity.
 */
public class AddCustomerActivity extends AppCompatActivity {

    private CoordinatorLayout customerContainer;
    private EditText nameC;
    private EditText addressC;
    private EditText phoneC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        setTitle("Add Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.add_menu, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (Utils.isValid(nameC, addressC, phoneC)) {
                saveCustomer();
            }
        }
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();

        return true;
    }

    private void saveCustomer() {
        DBHandler dbHandler = DB.getDB(getApplicationContext());
        String n, p, a;
        n = nameC.getText().toString();
        p = phoneC.getText().toString();
        a = addressC.getText().toString();
        Customer c = new Customer();
        c.setName(n);
        c.setAddress(a);
        c.setPhone(p);

        if (Utils.isValid(nameC, addressC, phoneC)) {
            dbHandler.getCustomerDA().save(c);
            Utils.showSnackbar(customerContainer, "Saved");
            Utils.clearTexts();
        } else
            Utils.showSnackbar(customerContainer, "Invalid Fields");//

    }

    private void initView() {
        customerContainer = findViewById(R.id.customerContainer);
        customerContainer = findViewById(R.id.customerContainer);
        nameC = findViewById(R.id.nameC);
        addressC = findViewById(R.id.addressC);
        phoneC = findViewById(R.id.phoneC);
    }
}
