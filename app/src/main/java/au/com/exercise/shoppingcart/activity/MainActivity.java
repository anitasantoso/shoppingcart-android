package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.data.Category;
import au.com.exercise.shoppingcart.db.DatabaseInitialiser;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.fragment.NavigationDrawerFragment;
import au.com.exercise.shoppingcart.fragment.ProductsFragment;
import au.com.exercise.shoppingcart.util.CurrentUser;
import au.com.exercise.shoppingcart.util.UserPref_;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    NavigationDrawerFragment mNavigationDrawerFragment;

    @AfterViews
    void setupViews() {
        getActionBar().setDisplayShowTitleEnabled(false);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        List<Category> categories = DatabaseMgr.getInstance().getCategories();
        if(categories.isEmpty()) {
            return;
        }
        ProductsFragment frag = ProductsFragment.newInstance(categories.get(position).getCategoryId());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            // updateActionBar();
        }
        if (id == R.id.action_view_cart) {
            startActivity(new Intent(this, ShoppingCartActivity_.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
