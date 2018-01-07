package com.ramzi.inventoryapp.productUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzi.inventoryapp.R;
import com.ramzi.inventoryapp.entity.Product;
import com.ramzi.inventoryapp.util.Extras;

/**
 * The type Product dialog.
 */
public class ProductDialog extends DialogFragment implements ProductFragment.OnProductSelected {

    /**
     * The On product selected.
     */
    OnProductSelected onProductSelected;

    @Override
    public void onSelect(Product product) {
        if(onProductSelected!=null) {
            onProductSelected.onSelect(product);
            getDialog().cancel();
        }
    }

    /**
     * Sets on product selected.
     *
     * @param onProductSelected the on product selected
     */
    public void setOnProductSelected(OnProductSelected onProductSelected) {
        this.onProductSelected = onProductSelected;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.product_dialog,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            getDialog().setCancelable(false);
            FragmentManager fragmentManager=getChildFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            ProductFragment productFragment=new ProductFragment();
            productFragment.setOnProductSelected(this);
            productFragment.setHasOptionsMenu(true);
            Bundle bundle=new Bundle();
            bundle.putString(Extras.mode,Extras.selectProduct);
            productFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragmentContainer,productFragment);
            fragmentTransaction.commit();
    }

    /**
     * The interface On product selected.
     */
    public interface OnProductSelected extends ProductFragment.OnProductSelected {
    }
}
