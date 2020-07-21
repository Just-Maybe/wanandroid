package com.example.wanandroid.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


/**
 * Created by LiuBin.
 */
public class ViewUtils {
    /**
     * 移除View上的ViewTreeObserver，兼容方法
     */
    public static void removeViewTreeObserver(View view, ViewTreeObserver.OnGlobalLayoutListener l) {
        if (view != null && view.getViewTreeObserver() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(l);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(l);
            }
        }
    }

    public static void setCompoundDrawables(final TextView textView, Drawable start,
                                            Drawable top, Drawable end, Drawable bottom) {

        if (start != null) {
            start.setBounds(0, 0, start.getIntrinsicWidth(), start.getIntrinsicHeight());
        }
        if (end != null) {
            end.setBounds(0, 0, end.getIntrinsicWidth(), end.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        textView.setCompoundDrawables(start, top, end, bottom);
    }

    public static void setCompoundDrawables(final TextView textView, int start,
                                            int top, int end, int bottom) {
        final Context context = textView.getContext();
        setCompoundDrawables(textView, start != 0 ? ContextCompat.getDrawable(context, start) : null,
                top != 0 ? ContextCompat.getDrawable(context, top) : null,
                end != 0 ? ContextCompat.getDrawable(context, end) : null,
                bottom != 0 ? ContextCompat.getDrawable(context, bottom) : null);
    }

    public static void setBackground(  View view, int res) {
        setBackground(view, ContextCompat.getDrawable(view.getContext(), res));
    }

    public static void setBackground(  View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static int getMaxEms(final TextView textView, final int margin) {
        if (textView.getText() == null) return 0;
        float w = textView.getPaint().measureText("啊");
        //float w1 = textView.getPaint().measureText("“");
        //float w2 = textView.getPaint().measureText("1");
        //float w3 = textView.getPaint().measureText("A");
        //TxYunVideoPlayerView.log(" 字符宽 "+ w + " 符号宽 " + w1 + " 数字宽 "+ w2 + " 英文符号宽 "+ w3);
        //String text = textView.getText().toString();
        //TxYunVideoPlayerView.log(" text　is : " + text);
        //Pattern p = Pattern.compile("([\\u4e00-\\u9fa5])");
        //Pattern p1 = Pattern.compile("([\\W])");
        //Pattern p2 = Pattern.compile("[0-9]");
        //Pattern p3 = Pattern.compile("([a-zA-Z])");
        //int l = 0,l1 = 0,l2 = 0,l3 = 0;
        //Matcher matcher;
        //if (p.matcher(text).matches()) {
        //   l = p.matcher(text).group().length();
        //}
        //if (p1.matcher(text).matches()) {
        //   l1 = p1.matcher(text).group().length();
        //}
        //if (p2.matcher(text).matches()) {
        //   l2 = p2.matcher(text).group().length();
        //}
        //if (p3.matcher(text).matches()) {
        //   l3 = p3.matcher(text).group().length();
        //}
        //TxYunVideoPlayerView.log("总数： " + text.length() + " 字符数目 "+ l + " 符号数 " + l1 + " 数字数 "+ l2 + " 英文数 "+ l3);

        int screenWdith = ScreenUtils.getScreenWidth(textView.getContext());
        float textWidth = screenWdith - margin;
        int maxEms = (int) Math.floor(textWidth / w);
        return maxEms;
    }

    public static boolean isSingleLineWithMargin(TextView textView, int offset) {
        String text = textView.getText().toString();
        float textWidth = textView.getPaint().measureText(text);
        float screenWdith = ScreenUtils.getScreenWidth(textView.getContext());
        return textWidth + offset < screenWdith;
    }

    public static boolean isSingleLine(final TextView textView, final int margin) {
        if (textView.getText() == null) return false;
        String text = textView.getText().toString();
        //TxYunVideoPlayerView.log("text： " + text);
        int screenWdith = ScreenUtils.getScreenWidth(textView.getContext());
        float textWidth = textView.getPaint().measureText(text);
        int padding = 0;
        Drawable leftDrawable = textView.getCompoundDrawables()[0];
        Drawable rightDrawable = textView.getCompoundDrawables()[2];
        if (leftDrawable != null) { // left
            int leftPading = leftDrawable.getIntrinsicWidth() + textView.getCompoundDrawablePadding();
            padding += leftPading;
        } else if (rightDrawable != null) { // right
            int rightPadding = rightDrawable.getIntrinsicWidth() + textView.getCompoundDrawablePadding();
            padding += rightPadding;
        }
        // span text
        if (textView.getText() instanceof Spanned) {
            Spanned spanned = (Spanned) textView.getText();
            ImageSpan[] spans = spanned.getSpans(0, spanned.length(), ImageSpan.class);
            if (spans != null && spans.length > 0) {
                for (ImageSpan span : spans) {
                    Drawable drawable = span.getDrawable();
                    Rect rect = drawable.getBounds();
                    int rectPadding = rect.right - rect.left;
                    //TxYunVideoPlayerView.log("spanDrawablePadding " + rectPadding);
                    padding += rectPadding;
                }
            }
        }
        //
        //TxYunVideoPlayerView.log("screenWdith： "
        //    + screenWdith
        //    + " textWidth "
        //    + textWidth
        //    + " margin "
        //    + margin
        //    + " padding "
        //    + padding);
        return textWidth + padding <= screenWdith - margin;
    }
}
