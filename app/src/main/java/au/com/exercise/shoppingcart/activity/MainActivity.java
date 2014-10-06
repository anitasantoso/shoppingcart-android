package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

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
import au.com.exercise.shoppingcart.util.UserPref_;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Pref
    UserPref_ userPref;

    NavigationDrawerFragment mNavigationDrawerFragment;

    @AfterInject
    void init() {
        DatabaseMgr.init(this);

        if (!userPref.hasInitDb().get()) {
            if (DatabaseInitialiser.run(this)) {
                userPref.edit().hasInitDb().put(true).apply();
            } else {
                // TODO handle initialisation error
            }
        }
    }

    @AfterViews
    void setupViews() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        List<Category> categories = DatabaseMgr.getInstance().getCategories();
        ProductsFragment frag = ProductsFragment.newInstance(categories.get(position).getCategoryId());

        // update the main content by replacing fragments
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
