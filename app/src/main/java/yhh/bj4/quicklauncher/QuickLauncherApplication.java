package yhh.bj4.quicklauncher;

import android.app.Application;
import android.content.Intent;

import yhh.bj4.quicklauncher.notification.NotificationLauncher;
import yhh.bj4.quicklauncher.notification.NotificationLauncherFactory;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class QuickLauncherApplication extends Application {
    private static NotificationLauncher sNotificationLauncher;

    @Override
    public void onCreate() {
        super.onCreate();
        sNotificationLauncher = NotificationLauncherFactory.getInstance(this).create();
        startService(new Intent(this, QuickLauncherService.class));
    }

    public static NotificationLauncher getNotificationLauncher() {
        return sNotificationLauncher;
    }

}
