package au.com.exercise.shoppingcart.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anita on 6/10/2014.
 */
public class AssetUtil {

    public static Drawable getDrawable(Context context, String name) {
        Drawable drawable = null;
        try {
            InputStream ims = context.getAssets().open(name);
            drawable = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {

        }
        return drawable;
    }
}
