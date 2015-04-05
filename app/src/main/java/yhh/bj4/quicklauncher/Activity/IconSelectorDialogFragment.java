package yhh.bj4.quicklauncher.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.Utils;
import yhh.bj4.quicklauncher.notification.NotificationLauncher;

/**
 * Created by yenhsunhuang on 15/4/2.
 */
public class IconSelectorDialogFragment extends DialogFragment {
    public interface Callback {
        public void onDialogItemSelected(ResolveInfo rInfo);
    }

    private Context mContext;
    private LayoutInflater mInflater;

    private ListView mContentView;
    private IconAdapter mIconAdapter;
    private NotificationLauncher mNotificationLauncher;
    private Callback mCallback;

    private final HashMap<String, Bitmap> mItemMap = new HashMap<String, Bitmap>();

    public void setVariables(Context context, NotificationLauncher nl, Callback cb) {
        mCallback = cb;
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNotificationLauncher = nl;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Iterator<Bitmap> iter = mItemMap.values().iterator();
        while (iter.hasNext()) {
            iter.next().recycle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    private void initComponents() {
        mIconAdapter = new IconAdapter();
        mContentView.setAdapter(mIconAdapter);
        mContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCallback != null) {
                    mCallback.onDialogItemSelected(mIconAdapter.getItem(position));
                    dismiss();
                }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContentView = (ListView) mInflater.inflate(R.layout.icon_selector_dialog, null);
        initComponents();
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();

        dialog.setView(mContentView, 0, 0, 0, 0);
        return dialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        resetDialogSize();
    }

    private void resetDialogSize() {
        final Activity activity = getActivity();
        final TypedValue value = new TypedValue();
        activity.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, value, true);
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final float listItemHeight = value.getDimension(metrics);
        if (listItemHeight <= 0)
            return;
        final int displayHeight = metrics.heightPixels;
        final int dialogHeight = (int) (displayHeight - 2 * listItemHeight);
        getDialog().getWindow().setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                dialogHeight);
    }

    private class IconAdapter extends BaseAdapter {
        private final List<ResolveInfo> mData;
        private int mDrawableSize;

        public IconAdapter() {
            mData = Utils.getAllMainActivities(mContext);
            mDrawableSize = mContext.getResources().getDimensionPixelSize(R.dimen.notification_launcher_settings_icon_size) / 2;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public ResolveInfo getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.icon_selector_dialog_item, null);
                holder = new ViewHolder();
                holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
                holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ResolveInfo info = getItem(position);
            final String key = info.activityInfo.packageName + info.activityInfo.name;
            Bitmap icon = mItemMap.get(key);
            if (icon == null) {
                icon = Utils.getBoundBitmap(mDrawableSize, info.loadIcon(mContext.getPackageManager()));
                mItemMap.put(key, icon);
            }
            holder.mIcon.setImageBitmap(icon);
            holder.mTitle.setText(info.loadLabel(mContext.getPackageManager()));
            return convertView;
        }

        private class ViewHolder {
            TextView mTitle;
            ImageView mIcon;
        }
    }
}
