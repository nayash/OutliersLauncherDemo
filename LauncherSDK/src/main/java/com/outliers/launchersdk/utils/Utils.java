package com.outliers.launchersdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.outliers.launchersdk.R;

public class Utils {

    public static String getApplicationName(Context context, ApplicationInfo appInfo) {
        int stringId = appInfo.labelRes;
        String name = "";
        if(stringId == 0){
            name = appInfo.nonLocalizedLabel != null? appInfo.nonLocalizedLabel.toString():"";
        }else{
            name = context.getPackageManager().getApplicationLabel(appInfo).toString();
        }
        return name;
    }
}
