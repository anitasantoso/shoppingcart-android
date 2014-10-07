package au.com.exercise.shoppingcart.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

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

@EFragment(R.layout.fragment_shopping_cart)
public class ShoppingCartFragment extends Fragment {

    @ViewById
    ListView cartItemsListView;

    @ViewById
    TextView emptyCartTextView;

    @ViewById
    Button checkoutButton;

    ShoppingCart cart;
    BaseAdapter listAdapter;
    List<ShoppingCartItem> cartItems;

    public ShoppingCartFragment() {
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
        cart = DatabaseMgr.getInstance().getShoppingCart(CurrentUser.getUser());
        cartItems = cart.getItems();
        cartItemsListView.setAdapter(listAdapter = new CartAdapter());
        updateViewState();
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
    }

    private void reloadCart() {
        cartItems = cart.getItems();
        listAdapter.notifyDataSetChanged();
        updateViewState();
    }

    class CartAdapter extends BaseAdapter {

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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.row_cart_item, null);
            }
            final ShoppingCartItem item = cartItems.get(i);
            Product p = item.getProduct();

            ImageView imgView = (ImageView) view.findViewById(R.id.cartProdImageView);
            imgView.setImageDrawable(AssetUtil.getDrawable(getActivity(), p.getImageName()));

            TextView nameTextView = (TextView) view.findViewById(R.id.cartProdNameTextView);
            nameTextView.setText(p.getProductName());

            TextView qtyTextView = (TextView) view.findViewById(R.id.qtyTextView);
            qtyTextView.setText("Qty: " + item.getQuantity());

            // edit
            Button editButton = (Button)view.findViewById(R.id.cartEditBtn);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartItemEditDialog.show(getActivity(), item);
                }
            });

            // delete
            Button deleteButton = (Button)view.findViewById(R.id.cartDeleteBtn);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    // confirmation
                    new AlertDialog.Builder(getActivity()).setMessage("Are you sure you want to remove selected item?")
                            .setTitle("Delete Item")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cart.remove(item);
                                    reloadCart();
                                    Toast.makeText(getActivity(), "Item has been removed", Toast.LENGTH_SHORT).show();
                                }
                            }).show();

                }
            });
            return view;
        }
    }
}
