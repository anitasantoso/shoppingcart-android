package au.com.exercise.shoppingcart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.data.Product;

/**
 * Created by Anita on 4/10/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "shopping_cart.db";
    public static final int DATABASE_VERSION = 1;
    private Dao<Product, Integer> productDao;
    private Dao<Category, Integer> categoryDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Product.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Product.class, true);

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
        if(categoryDao == null) {
            try {
                categoryDao = getDao(Category.class);
            } catch (java.sql.SQLException e) {
                Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            }
        }
        return categoryDao;
    }

    public Dao<Product, Integer> getProductDao() {
        if(productDao == null) {
            try {
                productDao = getDao(Product.class);
            } catch (java.sql.SQLException e) {
                Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            }
        }
        return productDao;
    }

//
//
//    // TODO this is where you pre-populate table
//    private void init(Context context) {
//        InputStream is = context.getResources().openRawResource(R.raw.db_init);
//        DataInputStream in = new DataInputStream(is);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        String strLine;
//
//        try {
//            while ((strLine = br.readLine()) != null) {
//               // tableDAO.updateRaw(strLine);
//            }
//            in.close();
//        } catch(Exception e) {
//
//        }
//    }
}
