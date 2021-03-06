package au.com.exercise.shoppingcart.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import de.greenrobot.event.EventBus;

/**
 * Created by Anita on 7/10/2014.
 */
public class CartItemEditDialog {

    public static void show(Context context, final ShoppingCartItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit item");

        // set custom view
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
        builder.setView(view);

        // configure spinner
        final Spinner spinner = ((Spinner) view.findViewById(R.id.dialogEditQtySpinner));
        Integer[] vals = new Integer[ShoppingCart.MAX_ITEM_QTY];
        for(int i=0; i< ShoppingCart.MAX_ITEM_QTY; i++) {
            vals[i] = i+1;
        }
        spinner.setAdapter(new ArrayAdapter<Integer>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, vals));
        spinner.setSelection(item.getQuantity() - 1); // preselect

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // dismiss and notify shopping cart
                item.setQuantity(((Integer)spinner.getSelectedItem()).intValue());
                EventBus.getDefault().post(new CartItemEditEvent(item));
            }
        });
        builder.show();
    }

    public static class CartItemEditEvent {
        public ShoppingCartItem item;

        CartItemEditEvent(ShoppingCartItem item) {
            this.item = item;
        }
    }
}
