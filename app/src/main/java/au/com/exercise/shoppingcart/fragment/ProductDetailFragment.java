package au.com.exercise.shoppingcart.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

import java.text.NumberFormat;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.AssetUtil;
import au.com.exercise.shoppingcart.util.CurrentUser;

@EFragment(R.layout.fragment_product_detail)
public class ProductDetailFragment extends Fragment {

    @FragmentArg
    int productId;

    @ViewById
    ImageView detailProdImageView;

    @ViewById
    TextView detailProdNameTextView;

    @ViewById
    TextView detailProdDescTextView;

    @ViewById
    TextView detailProdPriceTextView;

    Product prod;

    public static ProductDetailFragment newInstance(int productId) {
        ProductDetailFragment f = new au.com.exercise.shoppingcart.fragment.ProductDetailFragment_();
        Bundle b = new Bundle();
        b.putInt("productId", productId);
        f.setArguments(b);
        return f;
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void setupViews() {
        prod = DatabaseMgr.getInstance().getProduct(productId);
        if(prod == null) {
            // null for whatever reason
            Toast.makeText(getActivity(), "Product is currently unavailable", Toast.LENGTH_LONG).show();
            return;
        }
        detailProdImageView.setImageDrawable(AssetUtil.getDrawable(getActivity(), prod.getImageName()));
        detailProdNameTextView.setText(prod.getProductName());
        detailProdPriceTextView.setText(NumberFormat.getCurrencyInstance().format(prod.getUnitPrice()));
        detailProdDescTextView.setText(prod.getProductDesc());
    }

    @Click
    void addCartButtonClicked() {
        ShoppingCart cart = DatabaseMgr.getInstance().getShoppingCart(CurrentUser.getUser());
        ShoppingCartItem item = new ShoppingCartItem(prod, 1);
        cart.addItem(item);
    }
}
