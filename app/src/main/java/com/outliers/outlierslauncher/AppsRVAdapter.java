package com.outliers.outlierslauncher;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.outliers.launchersdk.models.AppModel;
import com.outliers.launchersdk.utils.Utils;

import java.util.ArrayList;

public class AppsRVAdapter extends RecyclerView.Adapter<AppsRVAdapter.AppsViewHolder> {

    public interface IAppsRVAdapter{
        void onItemClick(int position, AppModel appModel, @Nullable Bundle extras);
    }

    ArrayList<AppModel> appModels;
    Context context;
    private final IAppsRVAdapter parent;

    public AppsRVAdapter(ArrayList<AppModel> appModels, Context context, IAppsRVAdapter parent){
        this.appModels = appModels;
        this.context = context;
        this.parent = parent;
    }

    class AppsViewHolder extends RecyclerView.ViewHolder{
        ImageView appIcon;
        TextView tvAppName;
        public AppsViewHolder(View view){
            super(view);

            appIcon = view.findViewById(R.id.iv_app);
            tvAppName = view.findViewById(R.id.tv_app_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.onItemClick(getLayoutPosition(), appModels.get(getLayoutPosition()), null);
                }
            });
        }
    }

    @NonNull
    @Override
    public AppsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppsViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_app, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AppsViewHolder holder, int position) {
        AppModel model = appModels.get(position);

        holder.appIcon.setImageDrawable(model.getAppIcon());

        String name = Utils.isValidString(model.getAppName()) ?
                model.getAppName(): "Unknown";
        if(name.length() > 10){
            name = name.substring(0, 8)+"..";
        }
        holder.tvAppName.setText(name);
    }

    @Override
    public int getItemCount() {
        return appModels.size();
    }

}
