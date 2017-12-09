package cn.we.base.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.net.URLDecoder;

import cn.we.base.App;

/**
 * Toast统一管理类
 */
public class ToastUtils {

    public static boolean isShow = true;
    public static Toast toast = Toast.makeText(App.appContext, "", Toast.LENGTH_LONG);


    private ToastUtils() {
        /* cannot be momentiated */
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(URLDecoder.decode(message.toString()));
            toast.show();
        }
    }


    public static void showShortCenter(Context context, CharSequence message) {
        if (isShow) {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(URLDecoder.decode(message.toString()));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showShort(Context context, String message) {
        try {
            if (isShow) {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setText(URLDecoder.decode(message));
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void safetyShowShort(Activity activity, final String message) {
        try {
            if (isShow) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setText(URLDecoder.decode(message));
                        toast.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(URLDecoder.decode(message.toString()));
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

}