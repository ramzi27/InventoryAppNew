package com.ramzi.inventoryapp.productUi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ramzi on 01-Dec-17.
 */

public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.add)
    FloatingActionButton button;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.noContent)
    TextView no;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.productTitle)
    TextView productTitle;

    private SuperRecyclerAdapter<Product> productSuperRecyclerAdapter;
    private ArrayList<Product> products = new ArrayList<>();
    private String mode;
    private OnProductSelected onProductSelected;

    @Override
    public void onRefresh() {
        productSuperRecyclerAdapter.clearData();
        getProducts();
    }

    public void setOnProductSelected(OnProductSelected onProductSelected) {
        this.onProductSelected = onProductSelected;
    }

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
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(true);
        refreshLayout.setColorSchemeResources(R.color.refresh_toolbar_color,R.color.refresh_color);
        setHasOptionsMenu(true);
        if(getArguments().getString(Extras.mode)!=null){
            mode=getArguments().getString(Extras.mode);
        }
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
        if (mode.matches(Extras.showProducts)) {
            getActivity().setTitle("Products");
            productSuperRecyclerAdapter.setOnClickListener((view, position, element) -> {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                intent.putExtra(Extras.mode, Extras.updateProduct);
                intent.putExtra(Extras.product, Utils.toJson(element));
                startActivity(intent);
            });
        }
        else if (mode.matches(Extras.selectProduct)) {
            getActivity().setTitle("Select Product");
            productTitle.setVisibility(View.VISIBLE);
            productSuperRecyclerAdapter.setOnClickListener((view, position, element) -> {
                if (onProductSelected != null)
               onProductSelected.onSelect(element);
            });
        }
        getProducts();
    }

    private void getProducts() {
        Flowable<List<Product>> listFlowable = DB.getDB(getContext()).getProductDA().getAllProducts();
        listFlowable.subscribeOn(Schedulers.io())
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
                    refreshLayout.setRefreshing(false);
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mode.matches(Extras.showCustomers)) {
            inflater.inflate(R.menu.search_menu, menu);
            SearchView searchView = (SearchView) menu.getItem(1).getActionView();
            searchView.setQueryHint("search product");
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            DB.getDB(getContext()).getProductDA().deleteTable();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextSubmit(String s) {
//        Flowable<List<Product>> listFlowable = DB.getDB(getContext()).getProductDA().selectProduct(s);
//        listFlowable.subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(products1 -> {
//                    if (products1.size() > 0) {
//                        products.clear();
//                        products.addAll(products1);
//                        productSuperRecyclerAdapter.setElements(products);
//                        productSuperRecyclerAdapter.notifyDataSetChanged();
//                    } else {
//                        list.setVisibility(View.INVISIBLE);
//                        no.setVisibility(View.VISIBLE);
//                    }
//                });
//        return true;
        List<Product>searchedList=products.stream().filter(product -> product.getName().matches(s)).collect(Collectors.toList());
        if (searchedList.size()>0) {
            no.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
            productSuperRecyclerAdapter.setElements(searchedList);
            productSuperRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            no.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onClose() {
        productSuperRecyclerAdapter.setElements(products);
        productSuperRecyclerAdapter.notifyDataSetChanged();
        no.setVisibility(View.INVISIBLE);
        list.setVisibility(View.VISIBLE);
        return false;
    }

    public interface OnProductSelected {
        void onSelect(Product product);
    }
}
