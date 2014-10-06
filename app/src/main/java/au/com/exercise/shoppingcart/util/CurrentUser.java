package au.com.exercise.shoppingcart.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Currency;

import au.com.exercise.shoppingcart.data.User;
import au.com.exercise.shoppingcart.db.DatabaseInitialiser;
import au.com.exercise.shoppingcart.db.DatabaseMgr;

/**
 * Created by Anita on 6/10/2014.
 */
public class CurrentUser {

    static CurrentUser instance;
    private static User user;

    public static void init(Context context) {
        if (user == null) {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            user = DatabaseMgr.getInstance().getUser(deviceId);
        }
    }

    public static User getUser() {
        return  user;
    }
}
