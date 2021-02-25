package com.outliers.launchersdk.models;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.outliers.launchersdk.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AppModel implements Parcelable {

    String appName = "", packageName = "", mainActivityName = "", versionName = "";
    int appIconId = 0, versionCode = 0;
    Drawable appIcon;
    Intent launchIntent;

    public AppModel() {
    }

    protected AppModel(Parcel in) {
        appName = in.readString();
        packageName = in.readString();
        mainActivityName = in.readString();
        versionName = in.readString();
        appIconId = in.readInt();
        versionCode = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeString(mainActivityName);
        dest.writeString(versionName);
        dest.writeInt(appIconId);
        dest.writeInt(versionCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppModel> CREATOR = new Creator<AppModel>() {
        @Override
        public AppModel createFromParcel(Parcel in) {
            return new AppModel(in);
        }

        @Override
        public AppModel[] newArray(int size) {
            return new AppModel[size];
        }
    };

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMainActivityName() {
        return mainActivityName;
    }

    public void setMainActivityName(String mainActivityName) {
        this.mainActivityName = mainActivityName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getAppIconId() {
        return appIconId;
    }

    public void setAppIconId(int appIconId) {
        this.appIconId = appIconId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setAppIcon(Drawable drawable){
        this.appIcon = drawable;
    }

    public Intent getLaunchIntent(){
        return launchIntent;
    }

    public Drawable getAppIcon(){
        return appIcon;
    }

    public static ArrayList<AppModel> getAppModelsFromAppInfoList(List<ApplicationInfo> applicationInfos, Context context) {
        ArrayList<AppModel> appModels = new ArrayList<>();
        for (ApplicationInfo applicationInfo : applicationInfos) {
            AppModel appModel = new AppModel();
            appModel.setAppIconId(applicationInfo.icon);
            appModel.setAppName(Utils.getApplicationName(context, applicationInfo));
            appModel.setPackageName(applicationInfo.packageName);
            try {
                ComponentName componentName = context.getPackageManager().
                        getLaunchIntentForPackage(applicationInfo.packageName).getComponent();
                appModel.setMainActivityName(componentName.getClassName());
            } catch (NullPointerException ex) {

            }

            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                        appModel.getPackageName(), 0);
                appModel.setVersionCode(packageInfo.versionCode);
                appModel.setVersionName(packageInfo.versionName);
            } catch (Exception ex) {

            }
            appModels.add(appModel);
        }
        return appModels;
    }

    public static ArrayList<AppModel> getAppModelsFromPackageInfoList(List<PackageInfo> applicationInfos, Context context) {
        ArrayList<AppModel> appModels = new ArrayList<>();
        for (PackageInfo p : applicationInfos) {
            AppModel appModel = new AppModel();
            appModel.appName = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
            appModel.packageName = p.packageName;
            appModel.versionName = p.versionName;
            appModel.versionCode = p.versionCode;
            appModel.appIcon = p.applicationInfo.loadIcon(context.getPackageManager());
            appModel.launchIntent = context.getPackageManager().getLaunchIntentForPackage(p.packageName);
            appModels.add(appModel);
        }
        return appModels;
    }

}
