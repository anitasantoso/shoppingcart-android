package au.com.exercise.shoppingcart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import au.com.exercise.shoppingcart.data.ShoppingCartItem;
import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.ShoppingCart;
import au.com.exercise.shoppingcart.data.Product;
import au.com.exercise.shoppingcart.data.User;

/**
 * Created by Anita on 4/10/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "shopping_cart.db";
    public static final int DATABASE_VERSION = 1;
    private Dao<Product, Integer> productDao;
    private Dao<Category, Integer> categoryDao;
    private Dao<User, Integer> userDao;
    private Dao<ShoppingCart, Integer> shoppingCartDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            productDao = getDao(Product.class);
            categoryDao = getDao(Category.class);
            userDao = getDao(User.class);
            shoppingCartDao = getDao(ShoppingCart.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for(Class c : new Class[]{Category.class, Product.class, User.class, ShoppingCart.class, ShoppingCartItem.class})
                TableUtils.createTable(connectionSource, c);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for(Class c : new Class[]{Category.class, Product.class, User.class, ShoppingCart.class, ShoppingCartItem.class})
                TableUtils.dropTable(connectionSource, c, true);

            onCreate(db, connectionSource);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
        }
    }

    public void reset() {
        try {
            TableUtils.clearTable(getConnectionSource(), Product.class);
            TableUtils.clearTable(getConnectionSource(), Category.class);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
        }
    }

    public Dao<Category, Integer> getCategoryDao() {
        return categoryDao;
    }

    public Dao<Product, Integer> getProductDao() {
        return productDao;
    }

    public Dao<User, Integer> getUserDao() {
        return userDao;
    }

    public Dao<ShoppingCart, Integer> getShoppingCartDao() {
        return shoppingCartDao;
    }
}
