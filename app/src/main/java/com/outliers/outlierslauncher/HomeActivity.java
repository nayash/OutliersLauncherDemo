package com.outliers.outlierslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import com.outliers.launchersdk.models.AppModel;
import com.outliers.launchersdk.src.OutliersLauncherRoot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    OutliersLauncherRoot outliersLauncherRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outliersLauncherRoot = OutliersLauncherRoot.getInstance(this);
        ArrayList<AppModel> appModels = outliersLauncherRoot.getAllInstalledApps();
        Log.v("test", appModels.toString());
    }

    @Override
    public void onBackPressed() {

    }
}