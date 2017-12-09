package cn.we.base.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import cn.we.base.widget.base.FixButton;
import cn.we.base.widget.base.FixEditText;
import cn.we.base.widget.base.FixTextView;

/**
 * Created by cuiruolei on 2016/10/9.
 */

public class ViewUtils {
    private static int measuredHeight;
    private static int measuredWidth;

    public static void pwd_show_hide(EditText mEtPwd, ImageView target, @DrawableRes int visibleId, @DrawableRes int invisibleId, boolean defaultShow) {

        target.setImageResource(defaultShow ? visibleId : invisibleId);
        mEtPwd.setTransformationMethod(!defaultShow ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
        mEtPwd.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = mEtPwd.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    public static void imageEnable(ImageButton imageButton, @DrawableRes int enabledImgId, @DrawableRes int disableImagId, boolean enabled) {
        imageButton.setImageResource(enabled ? enabledImgId : disableImagId);
        imageButton.setClickable(enabled);
    }

    public static void imageEnable(ImageView imageView, @DrawableRes int enabledImgId, @DrawableRes int disableImagId, boolean enabled) {
        imageView.setImageResource(enabled ? enabledImgId : disableImagId);
        imageView.setClickable(enabled);
    }


    public static void underlineText(TextView textView, boolean isUnderline) {
        textView.getPaint().setFlags(isUnderline ? Paint.UNDERLINE_TEXT_FLAG : 0);
        textView.getPaint().setAntiAlias(true);
    }

    public static String TextView2String(TextView textView) {
        if (textView == null) {
            return "";
        }
        try {
            return new String(textView.getText().toString().trim().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFont(ViewGroup group, Typeface font, float textSize) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof EditText || v instanceof Button || v instanceof RadioButton
                    || v instanceof FixTextView || v instanceof FixButton || v instanceof FixEditText) {
                ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((TextView) v).getTextSize() + textSize);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font, textSize);
        }
    }

    public static int getViewHeight(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                measuredHeight = view.getMeasuredHeight();
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return measuredHeight;
    }

    public static int getViewWidth(final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                measuredWidth = view.getMeasuredWidth();
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return measuredWidth;
    }
}
