package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Anita on 6/10/2014.
 */
@DatabaseTable
public class ShoppingCartItem {

    @DatabaseField(generatedId = true, canBeNull =  false, columnName = "cart_item_id")
    private int cartItemId;

    @DatabaseField
    private int quantity;

    @DatabaseField(foreign = true, foreignAutoCreate=true, foreignAutoRefresh=true, columnName = "product_id")
    private Product product;

    @DatabaseField(foreign = true, foreignAutoCreate=true, foreignAutoRefresh=true, columnName = "cart_id")
    private ShoppingCart cart;

    public ShoppingCartItem() {}

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
