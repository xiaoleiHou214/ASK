package com.example.ask.util;

import android.content.Context;
import android.content.res.Resources;

public class ResourceUtil {

    public static String getStringByName(Context context, String name) {
        Resources res = context.getResources();
        Integer identifier = res.getIdentifier(name, "string", context.getPackageName());
        if (identifier == 0) {
            return null;
        } else {
            return res.getString(identifier);
        }
    }

    public static String[] getStringArrayByName(Context context, String name) {
        Resources res = context.getResources();
        Integer identifier = res.getIdentifier(name, "array", context.getPackageName());
        if (identifier == 0) {
            return null;
        } else {
            return res.getStringArray(identifier);
        }
    }
}
