package au.com.exercise.shoppingcart.util;

import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Anita on 5/10/2014.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface UserPref {

    boolean hasInitDb();
}
