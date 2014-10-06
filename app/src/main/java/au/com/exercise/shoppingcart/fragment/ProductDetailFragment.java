package au.com.exercise.shoppingcart.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;

import au.com.exercise.shoppingcart.R;

@EFragment(R.layout.fragment_product_detail)
public class ProductDetailFragment extends Fragment {

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void setupViews() {

    }
}
