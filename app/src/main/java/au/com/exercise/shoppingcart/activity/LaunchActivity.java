package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.db.DatabaseInitialiser;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.CurrentUser;
import au.com.exercise.shoppingcart.util.UserPref;
import au.com.exercise.shoppingcart.util.UserPref_;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        DatabaseMgr.init(this);
        UserPref_ userPref = new UserPref_(this);
        if (!userPref.hasInitDb().get()) {
            if (DatabaseInitialiser.run(this)) {

                // set flag
                userPref.edit().hasInitDb().put(true).apply();

                showMainActivity();
            }

            // handle db init error
            else {
                Toast.makeText(this, "Database initialisation error, please reinstall this app",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            showMainActivity();
        }
    }

    private void showMainActivity() {
        // go to main activity
        startActivity(new Intent(this, au.com.exercise.shoppingcart.activity.MainActivity_.class));
        finish();
    }
}
