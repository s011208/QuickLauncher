package yhh.bj4.quicklauncher.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by yenhsunhuang on 15/4/3.
 */
public class GestureController extends FrameLayout {
    private static final boolean DEBUG = true;
    private static final String TAG = "GestureController";

    private MainActivity mActivity;

    private final int mHeightThreshold, mWidthThreshold;

    private int mStartX, mStartY;

    public GestureController(Context context) {
        this(context, null);
    }

    public GestureController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mWidthThreshold = context.getResources().getDisplayMetrics().widthPixels / 4;
            mHeightThreshold = context.getResources().getDisplayMetrics().heightPixels / 4;
        } else {
            mWidthThreshold = context.getResources().getDisplayMetrics().widthPixels / 5;
            mHeightThreshold = context.getResources().getDisplayMetrics().heightPixels / 4;
        }
    }

    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mStartX = (int) ev.getX();
            mStartY = (int) ev.getY();
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            final boolean invokeGesture = checkGesture(ev);
            if (invokeGesture) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean checkGesture(MotionEvent ev) {
        final int deltaX = (int) (mStartX - ev.getX());
        final int deltaY = (int) (mStartY - ev.getY());
        if (isSwipeUp(deltaX, deltaY)) {
            if (DEBUG)
                Log.v(TAG, "swipe up");
            mActivity.showIconSettingsPanel(false);
            return true;
        }
        return false;
    }

    private boolean isSwipeUp(final int deltaX, final int deltaY) {
        return (Math.abs(deltaY) > mHeightThreshold && Math.abs(deltaX) < mWidthThreshold);
    }
}
