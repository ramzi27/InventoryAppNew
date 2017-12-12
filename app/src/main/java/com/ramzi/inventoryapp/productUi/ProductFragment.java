package com.ramzi.inventoryapp.productUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.util.Extras;
import com.ramzi.inventoryapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ramzi on 01-Dec-17.
 */

public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    @BindView(R.id.add)
    FloatingActionButton button;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.noContent)
    TextView no;

    private SuperRecyclerAdapter<Product> productSuperRecyclerAdapter;
    private ArrayList<Product> products = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddProductActivity.class);
            intent.putExtra(Extras.mode, Extras.addProduct);
            startActivity(intent);

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        productSuperRecyclerAdapter = new SuperRecyclerAdapter(list);
        list.setAdapter(productSuperRecyclerAdapter);
        productSuperRecyclerAdapter.setOnClickListener((view, position, element) -> {
            Intent intent = new Intent(getContext(), AddProductActivity.class);
            intent.putExtra(Extras.mode, Extras.updateProduct);
            intent.putExtra(Extras.product, Utils.toJson(element));
            startActivity(intent);
        });
        getProducts();
    }

    private void getProducts() {
        Flowable<List<Product>> listFlowable = DB.getDB(getContext()).getProductDA().getAllProducts();
        listFlowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products1 -> {
                    if (products1.size() > 0) {
                        no.setVisibility(View.INVISIBLE);
                        products.clear();
                        products.addAll(products1);
                        productSuperRecyclerAdapter.setElements(products);
                        productSuperRecyclerAdapter.notifyDataSetChanged();
                        list.setVisibility(View.VISIBLE);
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_product, menu);
        SearchView searchView = (SearchView) menu.getItem(1).getActionView();
        searchView.setQueryHint("search product");
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteProducts) {
            DB.getDB(getContext()).getProductDA().deleteTable();
            getProducts();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Flowable<List<Product>> listFlowable = DB.getDB(getContext()).getProductDA().selectProduct(s);
        listFlowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products1 -> {
                    if (products1.size() > 0) {
                        products.clear();
                        products.addAll(products1);
                        productSuperRecyclerAdapter.setElements(products);
                        productSuperRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onClose() {
        getProducts();
        return true;
    }
}
