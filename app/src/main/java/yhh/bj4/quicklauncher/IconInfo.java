package yhh.bj4.quicklauncher;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class IconInfo implements Comparable<IconInfo> {
    public static final int TYPE_APPLICATION = 0;
    public static final int TYPE_SHORTCUT = 1;

    private static final String JSON_PACKAGENAME = "packageName";
    private static final String JSON_CLASSNAME = "className";
    private static final String JSON_TYPE = "type";
    private static final String JSON_RANK = "rank";
    public String mPackageName;
    public String mClassName;
    public int mType;
    public int mRank;

    public IconInfo(ResolveInfo info, int rank) {
        mPackageName = info.activityInfo.packageName;
        mClassName = info.activityInfo.name;
        mRank = rank;
        mType = TYPE_APPLICATION;
    }

    public IconInfo(String pkg, String clz, int rank) {
        mPackageName = pkg;
        mClassName = clz;
        mRank = rank;
        mType = TYPE_APPLICATION;
    }

    public IconInfo(String rawString) {
        try {
            JSONObject json = new JSONObject(rawString);
            mType = json.getInt(JSON_TYPE);
            mRank = json.getInt(JSON_RANK);
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
        try {
            json.put(JSON_TYPE, iconInfo.mType);
            json.put(JSON_RANK, iconInfo.mRank);
            if (iconInfo.mType == TYPE_APPLICATION) {
                json.put(JSON_PACKAGENAME, iconInfo.mPackageName);
                json.put(JSON_CLASSNAME, iconInfo.mClassName);
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String toString() {
        return toJson(this).toString();
    }

    @Override
    public int compareTo(IconInfo another) {
        if (another == null) {
            return -1;
        }
        if (mRank < another.mRank)
            return -1;
        else if (mRank > another.mRank)
            return 1;
        else
            return 0;
    }

    public ResolveInfo getResolveInfo(List<ResolveInfo> infos) {
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(mPackageName)
                    && info.activityInfo.name.equals(mClassName)) {
                return info;
            }
        }
        return null;
    }
}
