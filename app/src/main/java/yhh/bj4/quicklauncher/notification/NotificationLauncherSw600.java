package yhh.bj4.quicklauncher.notification;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.RemoteViews;

import java.util.List;

import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class NotificationLauncherSw600 extends NotificationLauncher {

    public NotificationLauncherSw600(Context context) {
        super(context);
    }

    @Override
    public int getMaximumItemSize() {
        return NotificationLauncher.ITEM_OF_SW600;
    }

    @Override
    public RemoteViews getGridLauncher(boolean isSmall) {
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
}
