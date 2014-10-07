package au.com.exercise.shoppingcart.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.ViewById;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.activity.MainActivity;
import au.com.exercise.shoppingcart.activity.ProductDetailActivity;
import au.com.exercise.shoppingcart.activity.ProductDetailActivity_;
import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.fragment.ProductsFragment_;
import au.com.exercise.shoppingcart.util.AssetUtil;

@EFragment(R.layout.fragment_products)
public class ProductsFragment extends Fragment {

    @FragmentArg
    int categoryId;

    @ViewById
    TextView categoryTextView;

    @ViewById
    GridView prodGridView;

    public static ProductsFragment newInstance(int categoryId) {
        ProductsFragment frag = new ProductsFragment_();
        Bundle b = new Bundle();
        b.putInt("categoryId", categoryId);
        frag.setArguments(b);
        return frag;
    }

    public ProductsFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void setupViews() {
        final List<Product> products = DatabaseMgr.getInstance().getProducts(categoryId);
        if (products.isEmpty()) {
            return;
        }
        categoryTextView.setText(products.get(0).getCategory().getCategoryName());
        prodGridView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return products.size();
            }

            @Override
            public Object getItem(int i) {
                return products.get(i);
            }

            @Override
            public long getItemId(int i) {
                return products.get(i).getProductId();
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.grid_product, null);
                }
                Product p = products.get(i);

                ImageView imageView = (ImageView) view.findViewById(R.id.prodImageView);
                imageView.setImageDrawable(AssetUtil.getDrawable(getActivity(), p.getImageName()));

                TextView nameTextView = (TextView) view.findViewById(R.id.prodNameTextView);
                nameTextView.setText(p.getProductName());

                TextView priceTextView = (TextView) view.findViewById(R.id.prodPriceTextView);

                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                priceTextView.setText(formatter.format(p.getUnitPrice()));

                return view;
            }
        });
    }

    @ItemClick(R.id.prodGridView)
    void listViewClicked(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity_.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.getProductId());
        startActivity(intent);
    }
}
