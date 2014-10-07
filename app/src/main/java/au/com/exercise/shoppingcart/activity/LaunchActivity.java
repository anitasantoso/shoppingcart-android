package au.com.exercise.shoppingcart.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import au.com.exercise.shoppingcart.R;
import au.com.exercise.shoppingcart.db.DatabaseInitialiser;
import au.com.exercise.shoppingcart.db.DatabaseMgr;
import au.com.exercise.shoppingcart.util.UserPref_;

@EActivity(R.layout.activity_launch)
public class LaunchActivity extends Activity {

    @Pref
    UserPref_ userPref;

    @AfterViews
    void setupViews() {

        DatabaseMgr.init(this);
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
