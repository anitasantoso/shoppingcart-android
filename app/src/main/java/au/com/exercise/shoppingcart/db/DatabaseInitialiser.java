package au.com.exercise.shoppingcart.db;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Arrays;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.User;

/**
 * Created by Anita on 5/10/2014.
 */
public class DatabaseInitialiser {

    public static boolean run(Context context) {
        Category cat = new Category("Electronics");
        Product prod1 = new Product("Microwave Oven", "This is a microwave oven", cat, "microwave_oven.jpg", 120.0f);
        Product prod2 = new Product("Television", "This is a television", cat, "television.jpg", 1223.0f);
        Product prod3 = new Product("Vacuum Cleaner", "This is a vacuum cleaner", cat, "vacuum_cleaner.jpg", 345.0f);

        Category cat2 = new Category("Furniture");
        Product prod4 = new Product("Table", "This is a table", cat2, "table.jpg", 220.0f);
        Product prod5 = new Product("Chair", "This is a chair", cat2, "chair.jpg", 99.0f);
        Product prod6 = new Product("Almirah", "This is an almirah", cat2, "almirah.jpg", 420.0f);

        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        User user = new User("anonymous", "Anonymous", "User", deviceId);
        ShoppingCart cart = new ShoppingCart(user);

        boolean success = DatabaseMgr.getInstance().addProducts(Arrays.asList(new Product[]{prod1, prod2, prod3, prod4, prod5, prod6}));
        success &= DatabaseMgr.getInstance().createUser(user);
        success &= DatabaseMgr.getInstance().initCart(cart);

        if (!success) {
            DatabaseMgr.getInstance().reset();
        }
        return success;
    }
}
