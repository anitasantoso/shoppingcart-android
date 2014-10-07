package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

import java.text.NumberFormat;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.AssetUtil;
import au.com.exercise.shoppingcart.util.CurrentUser;

@EActivity(R.layout.activity_product_detail)
public class ProductDetailActivity extends Activity {

    public static final String EXTRA_PRODUCT_ID = "productId";

    @Extra
    int productId;

    @ViewById
    ImageView detailProdImageView;

    @ViewById
    TextView detailProdNameTextView;

    @ViewById
    TextView detailProdDescTextView;

    @ViewById
    TextView detailProdPriceTextView;

    @ViewById
    Spinner detailQtySpinner;

    Product prod;

    public ProductDetailActivity() {
        // Required empty public constructor
    }

    @AfterViews
    void setupViews() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        prod = DatabaseMgr.getInstance().getProduct(productId);
        getActionBar().setTitle(prod.getProductName());

        // if null for whatever reason
        if(prod == null) {
            Toast.makeText(this, "Product is currently unavailable", Toast.LENGTH_LONG).show();
            return;
        }

        // populate views
        detailProdImageView.setImageDrawable(AssetUtil.getDrawable(this, prod.getImageName()));
        detailProdNameTextView.setText(prod.getProductName());
        detailProdPriceTextView.setText(NumberFormat.getCurrencyInstance().format(prod.getUnitPrice()));
        detailProdDescTextView.setText(prod.getProductDesc());

        // qty dropdown
        Integer[] vals = new Integer[ShoppingCart.MAX_ITEM_QTY];
        for(int i=0; i< ShoppingCart.MAX_ITEM_QTY; i++) {
            vals[i] = i+1;
        }
        detailQtySpinner.setAdapter(new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, vals));
        detailQtySpinner.setSelection(0);
    }

    @Click
    void addCartButtonClicked() {
        ShoppingCart cart = DatabaseMgr.getInstance().getShoppingCart(CurrentUser.getUser());
        assert(cart != null);

        int qty = ((Integer)detailQtySpinner.getSelectedItem()).intValue();
        ShoppingCartItem item = new ShoppingCartItem(prod, qty);

        boolean error = false;
        try {
            cart.addItem(item);
        } catch (ShoppingCart.MaxQtyExceededException e) {
            error = true;
            new AlertDialog.Builder(this).setTitle("Error")
                    .setMessage("You can only add a maximum of " + ShoppingCart.MAX_ITEM_QTY + " items")
                    .setNegativeButton("OK", null)
                    .show();
        }
        if(!error) {
            Toast.makeText(this, "Item has been added to cart", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
