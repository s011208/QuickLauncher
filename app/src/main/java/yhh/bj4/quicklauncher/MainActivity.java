package yhh.bj4.quicklauncher;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import yhh.bj4.quicklauncher.notification.NotificationLauncher;
import yhh.bj4.quicklauncher.notification.NotificationLauncherSettings;


public class MainActivity extends Activity {
    private static final int FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS = 0;
    private NotificationLauncher mNotificationLauncher;

    private final HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        switchFragment(FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS);

    }

    private void switchFragment(final int type) {
        final Fragment fragment = getFragment(type);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initComponents() {
        mNotificationLauncher = QuickLauncherApplication.getNotificationLauncher();
    }

    private synchronized Fragment getFragment(final int type) {
        switch (type) {
            case FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS:
                Fragment rtn = mFragments.get(FRAGMENT_NOTIFICATION_LAUNCHER_SETTINGS);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
