package yhh.bj4.quicklauncher.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Iterator;

import yhh.bj4.quicklauncher.Activity.IconSettings.IconSettingsContainer;
import yhh.bj4.quicklauncher.QuickLauncherApplication;
import yhh.bj4.quicklauncher.R;
import yhh.bj4.quicklauncher.notification.NotificationLauncher;
import yhh.bj4.quicklauncher.notification.NotificationLauncherSettings;


public class MainActivity extends Activity {
    private static final int FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS = 0;


    private NotificationLauncher mNotificationLauncher;
    private GestureController mGestureController;
    private IconSettingsContainer mIconSettingsPanel;

    private boolean mShowIconSettingsPanel = false;

    private final HashMap<Integer, NotifiableFragment> mFragments = new HashMap<Integer, NotifiableFragment>();

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_PACKAGE_ADDED.equals(action) ||
                    Intent.ACTION_PACKAGE_ADDED.equals(action) ||
                    Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                final Iterator<NotifiableFragment> iter = mFragments.values().iterator();
                while (iter.hasNext()) {
                    iter.next().onPackageUpdated();
                }
            }
        }
    };

    private void registerReceiverWhenOnCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(mReceiver, filter);
    }

    private void unregisterReceiverWhenOnDestroy() {
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        switchFragment(FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS);
        registerReceiverWhenOnCreate();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiverWhenOnDestroy();
        super.onDestroy();
    }

    private void switchFragment(final int type) {
        final Fragment fragment = getFragment(type);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void initComponents() {
        mNotificationLauncher = QuickLauncherApplication.getNotificationLauncher();
        mIconSettingsPanel = (IconSettingsContainer) findViewById(R.id.icon_settings_panel);
        mGestureController = (GestureController) findViewById(R.id.gesture_controller);
        mGestureController.setActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (mShowIconSettingsPanel) {
            hideIconSettingsPanel(false);
            return;
        }
        super.onBackPressed();
    }

    public void showIconSettingsPanel(boolean immediately) {
        if (mIconSettingsPanel == null)
            return;
        if (mShowIconSettingsPanel)
            return;
        mIconSettingsPanel.showContainer(immediately);
        mShowIconSettingsPanel = true;
    }

    public void hideIconSettingsPanel(boolean immediately) {
        if (mIconSettingsPanel == null)
            return;
        if (!mShowIconSettingsPanel)
            return;
        mIconSettingsPanel.hideContainer(immediately);
        mShowIconSettingsPanel = false;
    }

    private synchronized Fragment getFragment(final int type) {
        switch (type) {
            case FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS:
                NotifiableFragment rtn = mFragments.get(FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS);
                if (rtn == null) {
                    rtn = NotificationLauncherSettings.newInstance();
                    mFragments.put(FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS, rtn);
                }
                return rtn;
            default:
                throw new RuntimeException("Wrong fragment type");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_icon_pack) {
            showIconSettingsPanel(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
