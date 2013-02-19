package com.github.hoverruan.libr.mobile.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Hover Ruan
 */
public class ToastUtils {

    public static void show(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void show(final Context context, final int resId) {
        if (context == null)
            return;

        show(context, context.getString(resId));
    }
}
