package yhh.bj4.quicklauncher;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class IconInfo implements Comparable<IconInfo> {
    public static final int TYPE_APPLICATION = 0;
    public static final int TYPE_SHORTCUT = 1;

    public String mPackageName;
    public String mClassName;
    public String mTitle;
    public int mIconType;
    public int mRank;
    public int mId;
    public Bitmap mIcon;

    public IconInfo(String pkg, String clz, String title, int rank, Bitmap icon) {
        mPackageName = pkg;
        mClassName = clz;
        mTitle = title;
        mRank = rank;
        mIconType = TYPE_APPLICATION;
        mIcon = icon;
    }

    public IconInfo(String packageName, String className, String title, int id, int iconType, int rank, Bitmap icon) {
        mPackageName = packageName;
        mClassName = className;
        mTitle = title;
        mId = id;
        mIconType = iconType;
        mRank = rank;
        mIcon = icon;
    }

    public Intent getIntent() {
        final Intent intent = new Intent();
        if (mIconType == TYPE_APPLICATION) {
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

    @Override
    public String toString() {
        return "mPackageName: " + mPackageName
                + ", mClassName: " + mClassName
                + ", mRank: " + mRank
                + ", mIconType: " + mIconType
                + ", mId: " + mId
                + ", mTitle: " + mTitle;
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
