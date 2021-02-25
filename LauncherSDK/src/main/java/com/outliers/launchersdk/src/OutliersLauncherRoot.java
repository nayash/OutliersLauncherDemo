package com.outliers.launchersdk.src;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.outliers.launchersdk.models.AppModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OutliersLauncherRoot {
    private static OutliersLauncherRoot outliersLauncherRoot;
    private Context context;
    private ArrayList<AppModel> appModels;

    public OutliersLauncherRoot(Context context) {
        this.context = context;
    }

    public static OutliersLauncherRoot getInstance(Context context) {
        if (outliersLauncherRoot == null)
            outliersLauncherRoot = new OutliersLauncherRoot(context);
        return outliersLauncherRoot;
    }

    public ArrayList<AppModel> getAllInstalledApps() {
        if(appModels == null) {
            appModels = AppModel.getAppModelsFromAppInfoList(context.getPackageManager().
                    getInstalledApplications(PackageManager.GET_META_DATA), context);
            sortApplicationsByName(appModels);
        }
        return appModels;
    }

    public void sortApplicationsByName(ArrayList<AppModel> appModels){
        Collections.sort(appModels, new Comparator<AppModel>() {
            @Override
            public int compare(AppModel o1, AppModel o2) {
                return o1.getAppName().compareTo(o2.getAppName());
            }
        });
    }
}
