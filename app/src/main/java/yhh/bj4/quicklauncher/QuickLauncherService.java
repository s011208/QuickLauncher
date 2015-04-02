package yhh.bj4.quicklauncher;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import yhh.bj4.quicklauncher.notification.NotificationLauncher;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class QuickLauncherService extends Service {
    private NotificationLauncher mNotificationLauncher;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationLauncher = QuickLauncherApplication.getNotificationLauncher();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startForeground(NotificationLauncher.NOTIFICATION_ID, mNotificationLauncher.getNotification());
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
