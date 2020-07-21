package com.example.wanandroid.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;


/**
 *
 */
public class KeyboardVisibilityEvent {

    public interface KeyboardVisibilityEventListener {

        void onVisibilityChanged(boolean isOpen);
    }

    private final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;


    /**
     * Set keyboard visiblity change event listener.
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    public static void setEventListener(
            @NonNull final Activity activity,
            @NonNull final KeyboardVisibilityEventListener listener) {


        final View activityRoot = getActivityRoot(activity);


        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {


                    private final Rect r = new Rect();


                    private final int visibleThreshold = Math.round(
                            CommonUtils.dp2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));


                    private boolean wasOpened = false;


                    @Override
                    public void onGlobalLayout() {
                        activityRoot.getWindowVisibleDisplayFrame(r);

                        int heightDiff = activityRoot.getRootView().getHeight() - r.height();


                        boolean isOpen = heightDiff > visibleThreshold;


                        //if (isOpen == wasOpened) {
                        //    // keyboard state has not changed
                        //    return;
                        //}

                        wasOpened = isOpen;
                        if (listener != null)
                            listener.onVisibilityChanged(isOpen);
                    }
                });
    }


    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();


        View activityRoot = getActivityRoot(activity);
        int visibleThreshold = Math
                .round(CommonUtils.dp2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));


        activityRoot.getWindowVisibleDisplayFrame(r);


        int heightDiff = activityRoot.getRootView().getHeight() - r.height();


        return heightDiff > visibleThreshold;
    }

    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static int getKeyboardVisible(Activity activity) {
        Rect r = new Rect();


        View activityRoot = getActivityRoot(activity);
        int visibleThreshold = Math
                .round(CommonUtils.dp2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));


        activityRoot.getWindowVisibleDisplayFrame(r);


        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

        if (heightDiff > visibleThreshold) {
            return heightDiff;
        }
        return 0;
    }

    private static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }
} 