package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anita on 6/10/2014.
 */
@DatabaseTable
public class ShoppingCart {

    @DatabaseField(generatedId = true, canBeNull =  false, columnName = "cart_id")
    private int cartId;

    @DatabaseField(foreign = true, foreignAutoCreate = true, canBeNull = false, columnName = "user_id")
    private User user;

    @ForeignCollectionField
    private ForeignCollection<ShoppingCartItem> items;

    public ShoppingCart() {}

    public ShoppingCart(User user) {
        this.user = user;
    }

    public void addItem(ShoppingCartItem item) {
        items.add(item);
    }
}
