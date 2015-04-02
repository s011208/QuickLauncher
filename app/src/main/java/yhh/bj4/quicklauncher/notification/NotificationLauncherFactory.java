package yhh.bj4.quicklauncher.notification;

import android.content.Context;

import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class NotificationLauncherFactory {
    private static NotificationLauncherFactory sInstance;

    public synchronized static NotificationLauncherFactory getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NotificationLauncherFactory(context.getApplicationContext());
        }
        return sInstance;
    }

    private final Context mContext;
    private final int mSizeType;

    private NotificationLauncherFactory(Context context) {
        mContext = context;
        mSizeType = mContext.getResources().getInteger(R.integer.type_size);
    }

    public NotificationLauncher create(){
        switch (mSizeType) {
            case Utils.SIZE_TYPE_SW360:
                return null;
            case Utils.SIZE_TYPE_SW600:
                return new NotificationLauncherSw600(mContext);
            case Utils.SIZE_TYPE_SW720:
                return null;
            default:
                return null;
        }
    }
}
