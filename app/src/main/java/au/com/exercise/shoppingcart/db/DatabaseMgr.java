package au.com.exercise.shoppingcart.db;

import android.content.Context;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.User;

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

    public ShoppingCart getShoppingCart(User user) {
        ShoppingCart cart = null;
        try {
            List<ShoppingCart> carts = getHelper().getShoppingCartDao().queryForEq("user_id", user.getUserId());
            if(carts != null && carts.size() > 0) {
                cart = carts.get(0);
            }
        } catch (SQLException e) {
        }
        return cart;
    }

    public boolean initCart(ShoppingCart cart) {
        try {
            getHelper().getShoppingCartDao().create(cart);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getUser(String deviceId) {
        User user = null;
        try {
            List<User> users = getHelper().getUserDao().queryForEq("device_id", deviceId);
            if(users != null && users.size() > 0) {
                user = users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean createUser(User user) {
        try {
            getHelper().getUserDao().create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    public Product getProduct(int productId) {
        Product product = null;
        try {
            product = getHelper().getProductDao().queryForId(productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
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
