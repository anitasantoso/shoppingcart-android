package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.AssetUtil;
import au.com.exercise.shoppingcart.util.CartItemEditDialog;
import au.com.exercise.shoppingcart.util.CurrentUser;
import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_shopping_cart)
public class ShoppingCartActivity extends Activity {

    @ViewById
    ListView cartItemsListView;

    @ViewById
    TextView emptyCartTextView;

    @ViewById
    Button checkoutButton;

    TextView totalPriceTextView;

    ShoppingCart cart;
    BaseAdapter listAdapter;
    List<ShoppingCartItem> cartItems;

    public ShoppingCartActivity() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @AfterViews
    void setupViews() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Shopping Cart");

        cartItems = Collections.EMPTY_LIST;
        cart = DatabaseMgr.getInstance().getShoppingCart(CurrentUser.getUser());
        cartItemsListView.setAdapter(listAdapter = new CartAdapter(this));

        View footer = LayoutInflater.from(this).inflate(R.layout.cart_footer, null);
        totalPriceTextView = (TextView)footer.findViewById(R.id.totalPriceTextView);
        cartItemsListView.addFooterView(footer);

        reloadCart();
    }

    @Click
    void checkoutButtonClicked() {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(CartItemEditDialog.CartItemEditEvent event) {
        ShoppingCartItem item = event.item;
        cart.update(item);
        reloadCart();
    }

    private void updateViewState() {
        boolean isEmpty = cartItems == null || cartItems.isEmpty();
        emptyCartTextView.setVisibility(isEmpty? View.VISIBLE : View.GONE);
        checkoutButton.setVisibility(isEmpty? View.GONE : View.VISIBLE);
        cartItemsListView.setVisibility(isEmpty? View.GONE : View.VISIBLE);
    }

    private void reloadCart() {
        cartItems = cart.getItems();
        listAdapter.notifyDataSetChanged();

        updateViewState();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for(ShoppingCartItem item : cartItems) {
            total += item.getQuantity()*item.getProduct().getUnitPrice();
        }
        totalPriceTextView.setText(NumberFormat.getCurrencyInstance().format(total));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CartAdapter extends BaseAdapter {
        Context context;

        CartAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return cartItems.size();
        }

        @Override
        public Object getItem(int i) {
            return cartItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_cart_item, null);
            }
            final ShoppingCartItem item = cartItems.get(i);
            final Product p = item.getProduct();

            ImageView imgView = (ImageView) view.findViewById(R.id.cartProdImageView);
            imgView.setImageDrawable(AssetUtil.getDrawable(context, p.getImageName()));
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, au.com.exercise.shoppingcart.activity.ProductDetailActivity_.class);
                    intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, p.getProductId());
                    startActivity(intent);
                }
            });

            TextView nameTextView = (TextView) view.findViewById(R.id.cartProdNameTextView);
            nameTextView.setText(p.getProductName());

            TextView qtyTextView = (TextView) view.findViewById(R.id.qtyTextView);
            qtyTextView.setText("Quantity: " + item.getQuantity());

            TextView priceTextView = (TextView) view.findViewById(R.id.itemPriceTextView);
            priceTextView.setText(NumberFormat.getCurrencyInstance().format(p.getUnitPrice()*item.getQuantity()));

            // edit
            Button editButton = (Button)view.findViewById(R.id.cartEditBtn);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartItemEditDialog.show(context, item);
                }
            });

            // delete
            Button deleteButton = (Button)view.findViewById(R.id.cartDeleteBtn);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    // confirmation
                    new AlertDialog.Builder(context).setMessage("Are you sure you want to remove selected item?")
                            .setTitle("Delete Item")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cart.remove(item);
                                    reloadCart();
                                    Toast.makeText(context, "Item has been removed", Toast.LENGTH_SHORT).show();
                                }
                            }).show();

                }
            });
            return view;
        }
    }
}
