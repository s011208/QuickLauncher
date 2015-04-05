package yhh.bj4.quicklauncher.Activity.IconSettings;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yenhsunhuang on 15/4/5.
 */
public class IconSettingsContainer extends ViewPager {
    private static final boolean DEBUG = true;
    private static final String TAG = "IconSettingsContainer";
    private static final int ANIMATOR_DURATION = 250;

    public static final int TYPE_ICON_PACK = 0;

    private int mType = 0;
    private final ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);

    public IconSettingsContainer(Context context) {
        this(context, null);
    }

    public IconSettingsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimator.setDuration(ANIMATOR_DURATION);
    }

    public void setType(final int type) {
        mType = type;
    }

    public void hideContainer(boolean immediately) {
        if (!immediately) {
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float value = (float) animation.getAnimatedValue();
                    setAlpha(1 - value);
                    setTranslationY(value * getHeight());
                }
            });
            mAnimator.addListener(new ValueAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.GONE);
                    setAlpha(0);
                    setTranslationY(getHeight());
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mAnimator.start();
        } else {
            setVisibility(View.GONE);
            setAlpha(0);
            setTranslationY(getHeight());
        }
    }

    public void showContainer(boolean immediately) {
        if (!immediately) {
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float value = (float) animation.getAnimatedValue();
                    setAlpha(value);
                    setTranslationY((1 - value) * getHeight());
                }
            });
            mAnimator.addListener(new ValueAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    setVisibility(View.VISIBLE);
                    setAlpha(0);
                    setTranslationY(getHeight());
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.VISIBLE);
                    setAlpha(1);
                    setTranslationY(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mAnimator.start();
        } else {
            setVisibility(View.VISIBLE);
            setAlpha(1);
            setTranslationY(0);
        }
    }
}
