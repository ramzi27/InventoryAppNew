package com.ramzi.inventoryapp.customerUi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicolkill.superrecyclerview.SuperRecyclerAdapter;
import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.db.DB;
import com.ramzi.inventoryapp.entity.Customer;
import com.ramzi.inventoryapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ramzi on 25-Nov-17.
 */

public class CustomerFragment extends Fragment {
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
        superRecyclerAdapter.removeElement(element);
        customers.remove(pos);
        Utils.showSnackbar(view, "deleted");
        DB.getDB(getContext()).getCustomerDA().delete(element);
        superRecyclerAdapter.notifyDataSetChanged();
        if (superRecyclerAdapter.getDataSize() == 0)
            no.setVisibility(View.VISIBLE);
    }
}
