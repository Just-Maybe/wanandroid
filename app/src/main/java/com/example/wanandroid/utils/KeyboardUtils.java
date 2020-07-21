package com.example.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardUtils {
  /**
   * 隐藏软键盘
   */
  public static void hideSoftKeyboard(Context context, EditText edit) {
    InputMethodManager imm =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
  }

  /**
   * 动态隐藏软键盘
   *
   * @param activity activity
   */
  public static void hideSoftInput(final Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    if (imm == null) return;
    View view = activity.getCurrentFocus();
    if (view == null) view = new View(activity);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void showSoftKeyboard(Context context, EditText edit) {
    InputMethodManager imm =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInputFromInputMethod(edit.getWindowToken(), InputMethodManager.SHOW_FORCED);
  }

  public static void show(final View view, final long delay) {
    if (view == null) {
      return;
    }
    final InputMethodManager manager =
        (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
    if (view.getWidth() == 0 && view.getHeight() == 0) {
      view.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              ViewUtils.removeViewTreeObserver(view, this);
              if (delay <= 0) {
                manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
              } else {
                view.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                    manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                  }
                }, delay);
              }
            }
          });
    } else {
      if (delay <= 0) {
        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
      } else {
        view.postDelayed(new Runnable() {
          @Override
          public void run() {
            manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
          }
        }, delay);
      }
    }
  }

  public static void hide(final View view, final long delay){
    if (view == null) {
      return;
    }
    final InputMethodManager manager =
            (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);

    if (view.getWidth() == 0 && view.getHeight() == 0) {
      view.getViewTreeObserver()
              .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                  ViewUtils.removeViewTreeObserver(view, this);
                  if (delay <= 0) {
                    manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
                  } else {
                    view.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
                      }
                    }, delay);
                  }
                }
              });
    } else {
      if (delay <= 0) {
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
      } else {
        view.postDelayed(new Runnable() {
          @Override
          public void run() {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
          }
        }, delay);
      }
    }
  }

}
