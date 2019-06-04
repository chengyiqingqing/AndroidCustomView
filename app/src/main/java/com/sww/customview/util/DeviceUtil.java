package com.sww.customview.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DeviceUtil {

    public static int getScreenWidth(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }

}
