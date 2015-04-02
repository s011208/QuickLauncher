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
import android.widget.RemoteViews;

import java.util.List;

import yhh.bj4.quicklauncher.IconInfo;
import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/3/31.
 */
public abstract class NotificationLauncher {

    public static final int ITEM_OF_SW360 = 10;
    public static final int ITEM_OF_SW600 = 12;
    public static final int ITEM_OF_sw720 = 12;

    private static final int NOTIFICATION_ID = NotificationLauncher.class.hashCode();

    final Context mContext;

    private final SharedPreferences mPrefs;

    public NotificationLauncher(Context context) {
        mContext = context;
        getMaximumItemSize();
        mPrefs = Utils.getPrefs(mContext);
        showNotificationLauncher();
    }

    public abstract int getMaximumItemSize();

    public void showNotificationLauncher() {
        final NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification, 0).setContent(getGridLauncher(true))
                .setPriority(Notification.PRIORITY_MAX).setOngoing(false).setAutoCancel(false);
        final Notification notification = notificationBuilder.build();
        notification.bigContentView = getGridLauncher(false);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public abstract RemoteViews getGridLauncher(boolean isSmall);

    void setTitleAndIcon(int index, int titleId, int iconId, int containerId, RemoteViews rv, List<ResolveInfo> activities
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
