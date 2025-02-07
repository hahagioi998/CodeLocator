package com.bytedance.tools.codelocator.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtils {

    private static String sAppName = null;

    public static synchronized String getAppName(Context context) {
        if (sAppName != null) {
            return sAppName;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            sAppName = context.getResources().getString(labelRes);
        } catch (Exception e) {
            Log.e("CodeLocator", "getAppName Failed " + Log.getStackTraceString(e));
        }
        return sAppName;
    }

    public static String getKeyword(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                final String keyword = getKeyword(((ViewGroup) view).getChildAt(i));
                if (keyword != null) {
                    return keyword;
                }
            }
        } else if (view instanceof TextView) {
            final TextView textView = (TextView) view;
            final CharSequence text = textView.getText();
            if (text != null && text.length() > 0) {
                return text.toString();
            } else if (textView.getHint() != null && textView.getHint().length() > 0) {
                return textView.getHint().toString();
            }
        }
        return null;
    }

}
