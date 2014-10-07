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
        Product prod1 = new Product("Microwave Oven", "The beautifully designed Electrolux e:line 30L microwave oven delivers outstanding performance with features including 900W power, quick defrost, multi-staged cooking and more. It features convection, microwave, grilling and a combination of cooking modes to allow quick preparation of a variety of dishes - meals can even be browned or crisped.", cat, "microwave_oven.jpg", 120.0f);
        Product prod2 = new Product("Television", "See more on TV than ever before with over 8 million individual pixels (3,840 x 2,160) compared to about 2 million (1,920 x 1,080) on your current HDTV1. Advanced picture processing also ensures that each of those pixels displays images with superb brightness and authentic detail. It's the highest resolution picture Sony has ever produced on a TV.", cat, "television.jpg", 1223.0f);
        Product prod3 = new Product("Vacuum Cleaner", "Dyson machines are tough. But they’re also efficiently engineered. We’re always looking for ways to use fewer materials, while at the same time making our machines stronger – doing more with less.", cat, "vacuum_cleaner.jpg", 345.0f);

        Category cat2 = new Category("Furniture");
        Product prod4 = new Product("Table", "Add a touch of industrial charm to your home with our Aspen range. Ideal in your dining or study area, this piece is a standout in any decor. Handcrafted from the finest solid recycled teak set atop a beige powdercoated steel base, this table stands firm and extremely versatile.", cat2, "table.jpg", 220.0f);
        Product prod5 = new Product("Chair", "Make a statement with the Amelia collection. Bold colours and contemporary lines are charactieristic of this stylish and highly functional range.", cat2, "chair.jpg", 99.0f);
        Product prod6 = new Product("Almirah", "Sourcing European timbers following the same principles as those of the Teak range, and designed in Belgium to exacting international trends, the furniture from Ethnicraft Oak range is timeless and will enhance any room.", cat2, "almirah.jpg", 420.0f);

        User user = new User("anonymous", "Anonymous", "User");
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
