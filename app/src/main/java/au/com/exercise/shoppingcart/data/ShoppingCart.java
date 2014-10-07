package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.exercise.shoppingcart.db.DatabaseMgr;

/**
 * Created by Anita on 6/10/2014.
 */
@DatabaseTable
public class ShoppingCart {

    @DatabaseField(generatedId = true, canBeNull = false, columnName = "cart_id")
    private int cartId;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false, columnName = "user_id")
    private User user;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<ShoppingCartItem> items;

    public ShoppingCart() {
    }

    public ShoppingCart(User user) {
        this.user = user;
    }

    public void addItem(ShoppingCartItem item) {

        boolean found = false;

        // if item already in the cart, increment
        for (ShoppingCartItem i : items) {
            if (i.getProduct().getProductId() == item.getProduct().getProductId()) {

                // update quantity
                i.setQuantity(i.getQuantity() + item.getQuantity());
                try {
                    items.update(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                found = true;
                break;
            }
        }

        // else add new item
        if (!found) {
            try {
                items.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(ShoppingCartItem item) {
        try {
            // if item already in the cart, increment
            for (ShoppingCartItem i : items) {
                if (i.getProduct().getProductId() == item.getProduct().getProductId()) {
                    i.setQuantity(item.getQuantity());
                    items.update(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(ShoppingCartItem item) {
        items.remove(item);
    }

    public List<ShoppingCartItem> getItems() {
        return items != null ? new ArrayList<ShoppingCartItem>(items) : Collections.EMPTY_LIST;
    }
}
