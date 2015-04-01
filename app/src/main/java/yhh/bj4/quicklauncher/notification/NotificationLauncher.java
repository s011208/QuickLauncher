package yhh.bj4.quicklauncher.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.List;

import yhh.bj4.quicklauncher.IconInfo;
import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/3/31.
 */
public class NotificationLauncher {
    private static NotificationLauncher sInstance;

    public synchronized static NotificationLauncher getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NotificationLauncher(context.getApplicationContext());
        }
        return sInstance;
    }

    public static final int ITEM_OF_SW360 = 10;
    public static final int ITEM_OF_SW600 = 12;
    public static final int ITEM_OF_sw720 = 12;

    private static final int NOTIFICATION_ID = NotificationLauncher.class.hashCode();


    private final Context mContext;

    private final int mSizeType;

    private int mMaximumItemSize;

    private final SharedPreferences mPrefs;

    private NotificationLauncher(Context context) {
        mContext = context;
        mSizeType = mContext.getResources().getInteger(R.integer.type_size);
        getMaximumItemSize();
        mPrefs = Utils.getPrefs(mContext);
        showNotificationLauncher();
    }

    private void getMaximumItemSize() {
        switch (mSizeType) {
            case Utils.SIZE_TYPE_SW360:
                mMaximumItemSize = ITEM_OF_SW360;
            case Utils.SIZE_TYPE_SW600:
                mMaximumItemSize = ITEM_OF_SW600;
            case Utils.SIZE_TYPE_SW720:
                mMaximumItemSize = ITEM_OF_sw720;
            default:
                mMaximumItemSize = ITEM_OF_SW360;
        }
    }

    public void showNotificationLauncher() {
        final NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher, 0).setContent(getGridLauncher(true))
                .setPriority(Notification.PRIORITY_MAX).setOngoing(false).setAutoCancel(false);
        final Notification notification = notificationBuilder.build();
        notification.bigContentView = getGridLauncher(false);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private RemoteViews getGridLauncher(boolean isSmall) {
        switch (mSizeType) {
            case Utils.SIZE_TYPE_SW360:
                return getGridLauncherSw360(isSmall);
            case Utils.SIZE_TYPE_SW600:
                return getGridLauncherSw600(isSmall);
            case Utils.SIZE_TYPE_SW720:
                return getGridLauncherSw720(isSmall);
            default:
                return getGridLauncherSw360(isSmall);
        }
    }

    private RemoteViews getGridLauncherSw360(boolean isSmall) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.grid_launcher);
        final PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> activities = Utils.getAllMainActivities(mContext);
//        setTitleAndIcon(0, R.id.title1, R.id.icon1, rv, activities, pm);
//        setTitleAndIcon(1, R.id.title2, R.id.icon2, rv, activities, pm);
//        setTitleAndIcon(2, R.id.title3, R.id.icon3, rv, activities, pm);
//        setTitleAndIcon(3, R.id.title4, R.id.icon4, rv, activities, pm);
//        setTitleAndIcon(4, R.id.title5, R.id.icon5, rv, activities, pm);
//        setTitleAndIcon(5, R.id.title6, R.id.icon6, rv, activities, pm);
//        setTitleAndIcon(6, R.id.title7, R.id.icon7, rv, activities, pm);
//        setTitleAndIcon(7, R.id.title8, R.id.icon8, rv, activities, pm);
//        setTitleAndIcon(8, R.id.title9, R.id.icon9, rv, activities, pm);
//        setTitleAndIcon(9, R.id.title10, R.id.icon10, rv, activities, pm);
//        setTitleAndIcon(10, R.id.title11, R.id.icon11, rv, activities, pm);
//        setTitleAndIcon(11, R.id.title12, R.id.icon12, rv, activities, pm);

        return rv;
    }

    private RemoteViews getGridLauncherSw600(boolean isSmall) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.grid_launcher);
        final PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> activities = Utils.getAllMainActivities(mContext);
        setTitleAndIcon(0, R.id.title1, R.id.icon1, R.id.container1, rv, activities, pm);
        setTitleAndIcon(1, R.id.title2, R.id.icon2, R.id.container2, rv, activities, pm);
        setTitleAndIcon(2, R.id.title3, R.id.icon3, R.id.container3, rv, activities, pm);
        setTitleAndIcon(3, R.id.title4, R.id.icon4, R.id.container4, rv, activities, pm);
        setTitleAndIcon(4, R.id.title5, R.id.icon5, R.id.container5, rv, activities, pm);
        setTitleAndIcon(5, R.id.title6, R.id.icon6, R.id.container6, rv, activities, pm);
        if (isSmall) {
            rv.setViewVisibility(R.id.container7, View.GONE);
            rv.setViewVisibility(R.id.container8, View.GONE);
            rv.setViewVisibility(R.id.container9, View.GONE);
            rv.setViewVisibility(R.id.container10, View.GONE);
            rv.setViewVisibility(R.id.container11, View.GONE);
            rv.setViewVisibility(R.id.container12, View.GONE);
        } else {
            setTitleAndIcon(6, R.id.title7, R.id.icon7, R.id.container7, rv, activities, pm);
            setTitleAndIcon(7, R.id.title8, R.id.icon8, R.id.container8, rv, activities, pm);
            setTitleAndIcon(8, R.id.title9, R.id.icon9, R.id.container9, rv, activities, pm);
            setTitleAndIcon(9, R.id.title10, R.id.icon10, R.id.container10, rv, activities, pm);
            setTitleAndIcon(10, R.id.title11, R.id.icon11, R.id.container11, rv, activities, pm);
            setTitleAndIcon(11, R.id.title12, R.id.icon12, R.id.container12, rv, activities, pm);
        }
        return rv;
    }

    private RemoteViews getGridLauncherSw720(boolean isSmall) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.grid_launcher);
        final PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> activities = Utils.getAllMainActivities(mContext);
//        setTitleAndIcon(0, R.id.title1, R.id.icon1, rv, activities, pm);
//        setTitleAndIcon(1, R.id.title2, R.id.icon2, rv, activities, pm);
//        setTitleAndIcon(2, R.id.title3, R.id.icon3, rv, activities, pm);
//        setTitleAndIcon(3, R.id.title4, R.id.icon4, rv, activities, pm);
//        setTitleAndIcon(4, R.id.title5, R.id.icon5, rv, activities, pm);
//        setTitleAndIcon(5, R.id.title6, R.id.icon6, rv, activities, pm);
//        if (isSmall) {
//            rv.setViewVisibility(R.id.title7, View.GONE);
//            rv.setViewVisibility(R.id.title8, View.GONE);
//            rv.setViewVisibility(R.id.title9, View.GONE);
//            rv.setViewVisibility(R.id.title10, View.GONE);
//            rv.setViewVisibility(R.id.title11, View.GONE);
//            rv.setViewVisibility(R.id.title12, View.GONE);
//            rv.setViewVisibility(R.id.icon7, View.GONE);
//            rv.setViewVisibility(R.id.icon8, View.GONE);
//            rv.setViewVisibility(R.id.icon9, View.GONE);
//            rv.setViewVisibility(R.id.icon10, View.GONE);
//            rv.setViewVisibility(R.id.icon11, View.GONE);
//            rv.setViewVisibility(R.id.icon12, View.GONE);
//
//        } else {
//            setTitleAndIcon(6, R.id.title7, R.id.icon7, rv, activities, pm);
//            setTitleAndIcon(7, R.id.title8, R.id.icon8, rv, activities, pm);
//            setTitleAndIcon(8, R.id.title9, R.id.icon9, rv, activities, pm);
//            setTitleAndIcon(9, R.id.title10, R.id.icon10, rv, activities, pm);
//            setTitleAndIcon(10, R.id.title11, R.id.icon11, rv, activities, pm);
//            setTitleAndIcon(11, R.id.title12, R.id.icon12, rv, activities, pm);
//        }
        return rv;
    }

    private void setTitleAndIcon(int index, int titleId, int iconId, int containerId, RemoteViews rv, List<ResolveInfo> activities
            , PackageManager pm) {
        final int iconSize = mContext.getResources().getDimensionPixelSize(R.dimen.notification_launcher_icon_size);
        String title = activities.get(index).loadLabel(pm).toString();
        Drawable icon = activities.get(index).loadIcon(pm);
        icon.setBounds(0, 0, iconSize, iconSize);
        Bitmap bIcon = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bIcon);
        icon.draw(canvas);
        canvas.setBitmap(null);
        rv.setTextViewText(titleId, title);
        rv.setImageViewBitmap(iconId, bIcon);
        IconInfo info = new IconInfo(activities.get(index));
        rv.setOnClickPendingIntent(containerId, info.getPendingIntent(mContext));
    }
}
