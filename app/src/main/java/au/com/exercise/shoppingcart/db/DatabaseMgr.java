package au.com.exercise.shoppingcart.db;

import android.content.Context;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;

/**
 * Created by Anita on 5/10/2014.
 */
public class DatabaseMgr {
    static private DatabaseMgr instance;

    static public void init(Context ctx) {
        if (null == instance) {
            instance = new DatabaseMgr(ctx);
        }
    }

    static public DatabaseMgr getInstance() {
        return instance;
    }

    private DatabaseHelper helper;

    private DatabaseMgr(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public void reset() {
        getHelper().reset();
    }

    public String[] getCategoryNames() {
        List<Category> categories = getCategories();
        String[] names = new String[categories.size()];
        for(int i=0; i<categories.size(); i++) {
            names[i] = categories.get(i).getCategoryName();
        }
        return names;
    }

    public List<Category> getCategories() {
        List<Category> categories = Collections.emptyList();
        try {
            categories = getHelper().getCategoryDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public boolean addProducts(List<Product> products) {
        for (Product product : products) {
            try {
                getHelper().getProductDao().create(product);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public List<Product> getProducts() {
        List<Product> products = null;
        try {
            products = getHelper().getProductDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProducts(int categoryId) {
        List<Product> products = Collections.emptyList();
        try {
            products = getHelper().getProductDao().queryForEq("category_id", categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
