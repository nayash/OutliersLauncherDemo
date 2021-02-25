package com.outliers.outlierslauncher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.outliers.launchersdk.models.AppModel;
import com.outliers.launchersdk.src.OutliersLauncherRoot;
import com.outliers.outlierslauncher.databinding.ActivityMainBinding;
import com.outliers.outlierslauncher.framework.RVItemDecoration;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AppsRVAdapter.IAppsRVAdapter {

    OutliersLauncherRoot outliersLauncherRoot;
    ActivityMainBinding binding;
    AppsRVAdapter adapter;
    ArrayList<AppModel> appModels, appModelsCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        outliersLauncherRoot = OutliersLauncherRoot.getInstance(this);
        appModels = outliersLauncherRoot.getAllInstalledApps();
        appModelsCopy = (ArrayList<AppModel>) appModels.clone();
        binding.rvApps.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvApps.addItemDecoration(new RVItemDecoration(4,
                getResources().getDimensionPixelSize(R.dimen.margin_default), true));
        adapter = new AppsRVAdapter(appModels, this, this);
        binding.rvApps.setAdapter(adapter);
        binding.etSearch.clearFocus();

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    appModels.clear();
                    appModels.addAll(appModelsCopy);
                    adapter.notifyDataSetChanged();
                    return;
                }
                appModels.clear();
                for(AppModel appModel : appModelsCopy){
                    if(appModel.getAppName().toLowerCase().contains(s.toString().toLowerCase()))
                        appModels.add(appModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.v("test", appModels.toString());
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemClick(int position, AppModel appModel, @Nullable Bundle extras) {
        if(appModel.getLaunchIntent() != null)
            startActivity(appModel.getLaunchIntent());
    }
}