package yhh.bj4.quicklauncher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by yenhsunhuang on 15/4/1.
 */
public class Utils {
    public static final int SIZE_TYPE_SW360 = 0;
    public static final int SIZE_TYPE_SW600 = 1;
    public static final int SIZE_TYPE_SW720 = 2;

    private static final String PREFS = "utils.prefs";

    public static List<ResolveInfo> getAllMainActivities(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return packageManager.queryIntentActivities(mainIntent, 0);
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
