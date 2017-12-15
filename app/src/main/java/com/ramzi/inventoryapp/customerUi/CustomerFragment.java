package com.ramzi.inventoryapp.customerUi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
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
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ramzi on 25-Nov-17.
 */

public class CustomerFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.noContent)
    TextView no;
    @BindView(R.id.add)
    FloatingActionButton button;
    private SuperRecyclerAdapter<Customer> superRecyclerAdapter;
    private ArrayList<Customer> customers = new ArrayList<>();

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
        getActivity().setTitle("Customers");
        setHasOptionsMenu(true);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddCustomerActivity.class);
            startActivity(intent);

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        superRecyclerAdapter = new SuperRecyclerAdapter(list);
        superRecyclerAdapter.setOnLongClickListener((view, position, element) -> {
            registerForContextMenu(view);
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.delete_contextmenu);
            popupMenu.setOnMenuItemClickListener(item -> {
                        buildDialog(element, view, position);
                        return true;
                    }
            );
            popupMenu.show();
        });
        list.setAdapter(superRecyclerAdapter);
        getCustomers();

    }

    private void buildDialog(Customer element, View view, int pos) {
        new AlertDialog.Builder(getContext()).setTitle("Delete Customer")
                .setMessage("Do You Want To Delete Customer?")
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    deleteCustomer(element, view, pos);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                })
                .show();

    }

    private void getCustomers() {
        Flowable<List<Customer>> customerFlowable = DB.getDB(getContext()).getCustomerDA().getAllCustomer();
        customerFlowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customers1 -> {
                    if (customers1.size() > 0) {
                        customers.clear();
                        customers.addAll(customers1);
                        superRecyclerAdapter.setElements(customers);
                        superRecyclerAdapter.notifyDataSetChanged();
                        list.setVisibility(View.VISIBLE);
                        no.setVisibility(View.INVISIBLE);
                    } else {
                        list.setVisibility(View.INVISIBLE);
                        no.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void deleteCustomer(Customer element, View view, int pos) {
        Utils.showSnackbar(view, "deleted");
        DB.getDB(getContext()).getCustomerDA().delete(element);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.getItem(1).getActionView();
        searchView.setQueryHint("search customers");
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.deleteAll)
            DB.getDB(getContext()).getCustomerDA().deleteTable();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextSubmit(String s) {
//        Flowable<List<Customer>> listFlowable = DB.getDB(getContext()).getCustomerDA().selectCustomer(s);
//        listFlowable.subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(products1 -> {
//                    if (products1.size() > 0) {
//                       customers.clear();
//                        customers.addAll(products1);
//                        superRecyclerAdapter.setElements(customers);
//                       superRecyclerAdapter.notifyDataSetChanged();
//                    } else {
//                        list.setVisibility(View.INVISIBLE);
//                        no.setVisibility(View.VISIBLE);
//                    }
//                });

        List<Customer>searchedList=customers.stream().filter(customer -> customer.getName().matches(s)).collect(Collectors.toList());
        superRecyclerAdapter.setElements(searchedList);
        superRecyclerAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onClose() {
        superRecyclerAdapter.setElements(customers);
        superRecyclerAdapter.notifyDataSetChanged();
        return false;
    }
}
