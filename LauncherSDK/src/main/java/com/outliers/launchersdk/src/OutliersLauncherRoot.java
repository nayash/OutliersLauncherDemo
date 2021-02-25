package com.outliers.launchersdk.src;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.outliers.launchersdk.models.AppModel;
import com.outliers.launchersdk.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
                    getInstalledApplications(0), context);
            appModels = AppModel.getAppModelsFromPackageInfoList(context.getPackageManager().
                    getInstalledPackages(0), context);
            sortApplicationsByName(appModels);
            filterOutUnknownApps(appModels);
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

    public void filterOutUnknownApps(ArrayList<AppModel> models){
        Iterator<AppModel> iterator = models.iterator();
        while(iterator.hasNext()){
            AppModel model = iterator.next();
            if(model.getAppIconId() == 0 && !Utils.isValidString(model.getAppName()))
                iterator.remove();
        }
    }
}
