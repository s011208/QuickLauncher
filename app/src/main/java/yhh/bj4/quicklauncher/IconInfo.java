package yhh.bj4.quicklauncher;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class IconInfo {
    public static final int TYPE_APPLICATION = 0;
    public static final int TYPE_SHORTCUT = 1;

    private static final String JSON_PACKAGENAME = "packageName";
    private static final String JSON_CLASSNAME = "className";
    private static final String JSON_TYPE = "type";
    public String mPackageName;
    public String mClassName;
    public int mType;

    public IconInfo(ResolveInfo info) {
        mPackageName = info.activityInfo.packageName;
        mClassName = info.activityInfo.name;
        mType = TYPE_APPLICATION;
    }

    public IconInfo(String pkg, String clz) {
        mPackageName = pkg;
        mClassName = clz;
        mType = TYPE_APPLICATION;
    }

    public IconInfo(String rawString) {
        try {
            JSONObject json = new JSONObject(rawString);
            mType = json.getInt(JSON_TYPE);
            if (mType == TYPE_APPLICATION) {
                mPackageName = json.getString(JSON_PACKAGENAME);
                mClassName = json.getString(JSON_CLASSNAME);
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Intent getIntent() {
        final Intent intent = new Intent();
        if (mType == TYPE_APPLICATION) {
            intent.setAction(Intent.ACTION_MAIN);
            intent.setClassName(mPackageName, mClassName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        } else {
        }
        return intent;
    }

    public PendingIntent getPendingIntent(Context context) {
        return PendingIntent.getActivity(context, 0, getIntent(), 0);
    }

    public static JSONObject toJson(IconInfo iconInfo) {
        JSONObject json = new JSONObject();
        if (iconInfo.mType == TYPE_APPLICATION) {
            try {
                json.put(JSON_PACKAGENAME, iconInfo.mPackageName);
                json.put(JSON_CLASSNAME, iconInfo.mClassName);
                json.put(JSON_TYPE, iconInfo.mType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
        }
        return json;
    }

    @Override
    public String toString() {
        return toJson(this).toString();
    }
}
