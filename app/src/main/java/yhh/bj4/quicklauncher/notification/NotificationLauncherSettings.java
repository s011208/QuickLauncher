package yhh.bj4.quicklauncher.notification;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yhh.bj4.quicklauncher.IconInfo;
import yhh.bj4.quicklauncher.QuickLauncherApplication;
import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class NotificationLauncherSettings extends Fragment {
    public synchronized static NotificationLauncherSettings newInstance() {
        NotificationLauncherSettings f = new NotificationLauncherSettings();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    private NotificationLauncher mNotificationLauncher;
    private View mContentView;
    private Context mContext;
    private GridView mGridView;
    private GridAdapter mGridAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationLauncher = QuickLauncherApplication.getNotificationLauncher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.grid_launcher_settings, container, false);
        mGridView = (GridView) mContentView.findViewById(R.id.notification_launcher_settings_grid);
        mGridView.setNumColumns(mNotificationLauncher.getMaximumItemSize() / 2);
        mGridAdapter = new GridAdapter(getActivity(), mNotificationLauncher);
        mGridView.setAdapter(mGridAdapter);
        android.util.Log.e("QQQQ", "CREATEVIEW");
        return mContentView;
    }

    private static class GridAdapter extends BaseAdapter {
        private final Context mContext;
        private final LayoutInflater mInflater;
        private final IconInfo[] mIconInfos;
        private final NotificationLauncher mNotificationLauncher;
        private List<ResolveInfo> mResolveInfos;

        public GridAdapter(final Context context, final NotificationLauncher nl) {
            mContext = context;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mNotificationLauncher = nl;
            mIconInfos = mNotificationLauncher.getIconInfos();
            Log.d("QQQQ", "count: " + getCount());
            initData();
        }

        private void initData() {
            // TODO remember to update when package changed
            mResolveInfos = Utils.getAllMainActivities(mContext);
        }

        @Override
        public int getCount() {
            return mIconInfos.length;
        }

        @Override
        public IconInfo getItem(int position) {
            return mIconInfos[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.grid_launcher_settings_item, null);
                holder = new ViewHolder();
                holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final IconInfo info = getItem(position);
            final ResolveInfo rInfo = info.getResolveInfo(mResolveInfos);
            holder.mIcon.setImageDrawable(rInfo.loadIcon(mContext.getPackageManager()));
            holder.mTitle.setText(rInfo.loadLabel(mContext.getPackageManager()));
            Log.d("QQQQ", "position: " + position);
            return convertView;
        }

        private static class ViewHolder {
            TextView mTitle;
            ImageView mIcon;
        }
    }

}
