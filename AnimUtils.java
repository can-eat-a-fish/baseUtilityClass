package cn.we.base.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.LinkedList;
import java.util.List;

import io.codetail.animation.ViewAnimationUtils;

import static android.view.View.LAYER_TYPE_NONE;
import static android.view.View.LAYER_TYPE_SOFTWARE;

/**
 * Created by cuiruolei on 2016/10/13.
 */

public class AnimUtils {
    private static List<ObjectAnimator> mWobbleAnimators = new LinkedList<ObjectAnimator>();

    public static ViewPropertyAnimator translationY(View targetView, int duration, float distance) {
        return ViewPropertyAnimator.animate(targetView).setDuration(duration).setInterpolator(new LinearInterpolator()).translationY(distance);
    }

    public static ViewPropertyAnimator rotation(View targetView, int duration, float value) {
        return ViewPropertyAnimator.animate(targetView).setDuration(duration).setInterpolator(new LinearInterpolator()).rotation(value);
    }

    public static ViewPropertyAnimator translationY(View targetView, int duration, float distance, float alpha) {
        return ViewPropertyAnimator.animate(targetView).alpha(alpha).setDuration(duration).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(distance);
    }

    public static ViewPropertyAnimator translationY(View targetView, float distance) {
        return ViewPropertyAnimator.animate(targetView).setInterpolator(new LinearInterpolator()).translationY(distance);
    }

    public static void translationX(View targetView, int duration, float... distance) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationX", distance);
        animator.setDuration(duration);
        animator.setRepeatCount(1);
        animator.start();
    }

    public static ValueAnimator timerAnimation(long startTime, long endTime, final long duration, long delay, final OnAnimationListener onAnimationListener) {
        ValueAnimator animator = ValueAnimator.ofFloat(endTime, startTime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                onAnimationListener.onAnimationUpdate(valueAnimator);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                onAnimationListener.onAnimationStart(animator);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onAnimationListener.onAnimationEnd(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                onAnimationListener.onAnimationCancel(animator);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                onAnimationListener.onAnimationRepeat(animator);
            }
        });

        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.start();
        return animator;
    }

    //展开，收起动画
    public static void switchVisibleBottomAnim(final View view, final boolean switchEnable, int height, int marginHeight) {
        view.setVisibility(View.VISIBLE);
        final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        ValueAnimator marginAnimator = null;
        ValueAnimator animator = null;
        if (switchEnable) {
            marginAnimator = ValueAnimator.ofInt(0, marginHeight);
            animator = ValueAnimator.ofInt(-height, 0);
        } else {

            animator = ValueAnimator.ofInt(0, -height);
            marginAnimator = ValueAnimator.ofInt(marginHeight, 0);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(0, 0, 0, (Integer) valueAnimator.getAnimatedValue());
            }
        });
        marginAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });


        final ValueAnimator finalMarginAnimator = marginAnimator;
        finalMarginAnimator.setInterpolator(new LinearInterpolator());
        animator.setInterpolator(new LinearInterpolator());
        finalMarginAnimator.start();
        animator.start();

    }

    //展开，收起动画
    public static void switchVisibleMarginAnim(final View view, final boolean switchEnable, int marginHeight) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator marginAnimator = null;
        if (switchEnable) {
            marginAnimator = ValueAnimator.ofInt(marginHeight, 0);
        } else {
            marginAnimator = ValueAnimator.ofInt(0, marginHeight);
        }

        marginAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                layoutParams.topMargin = (Integer) valueAnimator.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });
        marginAnimator.setDuration(3000);
//        marginAnimator.setInterpolator(new LinearInterpolator());
        marginAnimator.start();

    }

    //展开，收起动画
    public static void switchVisibleBottomAnim(final View view, final boolean switchEnable, int height) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = null;
        if (switchEnable) {
            animator = ValueAnimator.ofInt(-height, 0);
        } else {
            animator = ValueAnimator.ofInt(0, -height);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(0, 0, 0, (Integer) valueAnimator.getAnimatedValue());
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    } //展开，收起动画

    public static void switchVisibleBottomAnim(final View view, final boolean switchEnable, int height, long duration) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = null;
        if (switchEnable) {
            animator = ValueAnimator.ofInt(-height, 0);
        } else {
            animator = ValueAnimator.ofInt(0, -height);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(0, 0, 0, (Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }
    //展开，收起动画

    public static ValueAnimator switchVisibleUpAnim(final View view, final boolean switchEnable, int height) {
        ValueAnimator animator = null;
        if (switchEnable) {
            animator = ValueAnimator.ofInt(-height, 0);
        } else {
            animator = ValueAnimator.ofInt(0, -height);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(0, (int) valueAnimator.getAnimatedValue(), 0, 0);
            }
        });
        animator.setDuration(250);
//        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        return animator;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void stopWobble(boolean resetRotation) {
        for (Animator wobbleAnimator : mWobbleAnimators) {
            wobbleAnimator.cancel();
        }
        mWobbleAnimators.clear();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void restartWobble() {
        stopWobble(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ObjectAnimator animateWobble(View v) {
        ObjectAnimator animator = createBaseWobble(v);
        animator.setFloatValues(-2, 2);
        animator.start();
        mWobbleAnimators.add(animator);
        return animator;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ObjectAnimator animateWobbleInverse(View v) {
        ObjectAnimator animator = createBaseWobble(v);
        animator.setFloatValues(2, -2);
        animator.start();
        mWobbleAnimators.add(animator);
        return animator;
    }

    public static boolean isPreLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ObjectAnimator createBaseWobble(final View v) {

        if (!isPreLollipop())
            v.setLayerType(LAYER_TYPE_SOFTWARE, null);

        ObjectAnimator animator = new ObjectAnimator();
        animator.setDuration(180);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setPropertyName("rotation");
        animator.setTarget(v);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(LAYER_TYPE_NONE, null);
            }
        });
        return animator;
    }

    public static SpringAnimation BoundAnim(View view, float startVelocity) {
        SpringForce springForce = new SpringForce(0)
                .setDampingRatio(0.2f)
                .setStiffness(SpringForce.STIFFNESS_LOW);
        final SpringAnimation anim = new SpringAnimation(view, SpringAnimation.TRANSLATION_Y)
                .setSpring(springForce).setStartVelocity(-startVelocity);
        anim.start();
        return anim;
    }

    public interface OnAnimationListener {
        void onAnimationStart(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationCancel(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    public abstract class AnimationListenerAdapter implements OnAnimationListener {
        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
        }
    }


    public static void circleAnimation(View view, MotionEvent event) {
        view.bringToFront();
        view.setVisibility(View.VISIBLE);

        final float finalRadius =
                (float) Math.hypot(view.getWidth() / 2f, view.getHeight() / 2f) + hypo(
                        view, event);

        Animator revealAnimator =
                ViewAnimationUtils.createCircularReveal(view, (int) event.getX(), (int) event.getY(), 0,
                        finalRadius, View.LAYER_TYPE_HARDWARE);

        revealAnimator.setDuration(2000);
        revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
        revealAnimator.start();
    }

    private static float hypo(View view, MotionEvent event) {
        Point p1 = new Point((int) event.getX(), (int) event.getY());
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }

}
