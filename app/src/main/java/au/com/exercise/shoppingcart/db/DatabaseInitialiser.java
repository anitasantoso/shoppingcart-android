package au.com.exercise.shoppingcart.db;

import android.content.Context;

import java.util.Arrays;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;

/**
 * Created by Anita on 5/10/2014.
 */
public class DatabaseInitialiser {

    public static boolean run(Context context) {


        Category cat = new Category("electronics");
        Product prod1 = new Product("microwave oven", "This is a microwave oven", cat, "microwave_oven.jpg", 120.0f);
        Product prod2 = new Product("television", "This is a television", cat, "television.jpg", 1223.0f);
        Product prod3 = new Product("vacuum cleaner", "This is a vacuum cleaner", cat, "vacuum_cleaner.jpg", 345.0f);

        Category cat2 = new Category("furniture");
        Product prod4 = new Product("table", "This is a table", cat2, "table.jpg", 220.0f);
        Product prod5 = new Product("chair", "This is a chair", cat2, "chair.jpg", 99.0f);
        Product prod6 = new Product("almirah", "This is an almirah", cat2, "almirah.jpg", 420.0f);

        boolean success = DatabaseMgr.getInstance().addProducts(Arrays.asList(new Product[]{prod1, prod2, prod3, prod4, prod5, prod6}));
        if (!success) {
            DatabaseMgr.getInstance().reset();
        }
        return success;
    }
}
