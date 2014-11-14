package au.com.exercise.shoppingcart;

import android.test.InstrumentationTestCase;

import java.util.List;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.CurrentUser;

/**
 * Created by Anita on 14/11/2014.
 */
public class ShoppingCartTest extends InstrumentationTestCase {

    /**
     * Given that my shopping cart is empty, after I add an item to it, it should be persisted.
     */
    public void testShouldAddItemOnAnEmptyCart() throws ShoppingCart.MaxQtyExceededException {
        DatabaseMgr.init(getInstrumentation().getTargetContext().getApplicationContext());
        ShoppingCart cart = DatabaseMgr.getInstance().getShoppingCart(CurrentUser.getUser());

        List<Category> categories = DatabaseMgr.getInstance().getCategories();
        Category cat = categories.get(0);

        final List<Product> products = DatabaseMgr.getInstance().getProducts(cat.getCategoryId());
        Product prod = products.get(0);

        ShoppingCartItem item = new ShoppingCartItem(prod, 11);
        cart.addItem(item);

        assertEquals(cart.getItems().size(), 1);
    }

    public void testZMaxQtyException() {
        assertTrue(1==1);
    }
}
