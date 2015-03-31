package yhh.bj4.quicklauncher.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.widget.RemoteViews;

import yhh.bj4.quicklauncher.R;

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

    private static final int NOTIFICATION_ID = NotificationLauncher.class.hashCode();

    private final Context mContext;

    private final LayoutInflater mInflater;

    private NotificationLauncher(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        showNotificationLauncher();
    }

    public void showNotificationLauncher() {
        final NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher, 0)
                .setPriority(Notification.PRIORITY_MAX).setOngoing(false).setAutoCancel(false);
        final Notification notification = notificationBuilder.build();
        notification.bigContentView = getGridLauncher();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private RemoteViews getGridLauncher() {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.grid_launcher);
        return rv;
    }
}
