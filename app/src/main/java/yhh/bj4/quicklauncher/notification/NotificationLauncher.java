package yhh.bj4.quicklauncher.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import yhh.bj4.quicklauncher.IconInfo;
import yhh.bj4.quicklauncher.QuickLauncherService;
import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/3/31.
 */
public abstract class NotificationLauncher {
    static final boolean DEBUG = true;

    public static final int ITEM_OF_SW360 = 10;
    public static final int ITEM_OF_SW600 = 12;
    public static final int ITEM_OF_sw720 = 12;

    public static final int NOTIFICATION_ID = NotificationLauncher.class.hashCode();

    final Context mContext;

    private final SharedPreferences mPrefs;

    final NotificationLauncherIconInfoDatabase mDatabase;

    private final int mIconSize;

    public NotificationLauncher(Context context) {
        mContext = context;
        mIconSize = mContext.getResources().getDimensionPixelSize(R.dimen.notification_launcher_icon_size);
        getMaximumItemSize();
        mPrefs = Utils.getPrefs(mContext);
        mDatabase = NotificationLauncherIconInfoDatabase.getInstance(mContext);
        mDatabase.createTablesIfNeeded(getPreferenceKey());
        if (mDatabase.isEmpty(getPreferenceKey())) {
            insertDefaultItems();
        }
    }

    private void insertDefaultItems() {
        List<ResolveInfo> activities = Utils.getAllMainActivities(mContext);
        PackageManager pm = mContext.getPackageManager();
        ArrayList<IconInfo> infos = new ArrayList<IconInfo>();
        for (int i = 0; i < getMaximumItemSize() && i < activities.size(); i++) {
            final ResolveInfo info = activities.get(i);
            infos.add(new IconInfo(info.activityInfo.packageName, info.activityInfo.name, info.loadLabel(pm).toString(), i, Utils.getBoundBitmap(mIconSize, info.loadIcon(pm))));
        }
        mDatabase.setIconInfos(getPreferenceKey(), infos);
    }

    public abstract int getMaximumItemSize();

    public abstract RemoteViews getGridLauncher(boolean isSmall);

    public abstract String getPreferenceKey();

    public Notification getNotification() {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher, 0).setContent(getGridLauncher(true))
                .setPriority(Notification.PRIORITY_MAX).setOngoing(false).setAutoCancel(false);
        final Notification notification = notificationBuilder.build();
        notification.bigContentView = getGridLauncher(false);
        return notification;
    }

    public void notifyItemChanged() {
        mContext.startService(new Intent(mContext, QuickLauncherService.class));
    }

    public IconInfo[] getIconInfos() {
        ArrayList<IconInfo> infos = mDatabase.getIconInfos(getPreferenceKey());
        IconInfo[] rtn = new IconInfo[infos.size()];
        for (int i = 0; i < infos.size(); i++) {
            rtn[i] = infos.get(i);
        }
        return rtn;
    }

    void setTitleAndIcon(int index, int titleId, int iconId, int containerId, RemoteViews rv) {
        IconInfo info = getIconInfos()[index];
        rv.setTextViewText(titleId, info.mTitle);
        rv.setImageViewBitmap(iconId, info.mIcon);
        rv.setOnClickPendingIntent(containerId, info.getPendingIntent(mContext));
    }

    public void setIconInfo(int rank, ResolveInfo info) {
        boolean find = false;
        for (IconInfo iconInfo : getIconInfos()) {
            if (iconInfo.mRank == rank) {
                iconInfo.mPackageName = info.activityInfo.packageName;
                iconInfo.mClassName = info.activityInfo.name;
                iconInfo.mTitle = info.loadLabel(mContext.getPackageManager()).toString();
                iconInfo.mIcon.recycle();
                iconInfo.mIcon = Utils.getBoundBitmap(mIconSize, info.loadIcon(mContext.getPackageManager()));
                mDatabase.updateIconInfo(getPreferenceKey(), iconInfo);
                find = true;
                break;
            }
        }
        if (find) {
            notifyItemChanged();
        }
    }
}
