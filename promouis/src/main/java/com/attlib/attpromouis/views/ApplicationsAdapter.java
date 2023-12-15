package com.attlib.attpromouis.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.attlib.attpromouis.Constants;
import com.attlib.attpromouis.R;
import com.attlib.attpromouis.models.ApplicationResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ApplicationResult.ApplicationInfo> mListApp;
    private OnClickButtonsListener mListener;
    private static final int DEFAULT_ITEM_LAYOUT_ID = R.layout.item_application_default_view;
    private int itemLayoutId = DEFAULT_ITEM_LAYOUT_ID;

    private int rewardCoinsNumber = Constants.DEFAULT_DOWNLOAD_APP_COINS;

    public void setRewardCoinsNumber(int rewardCoinsNumber) {
        this.rewardCoinsNumber = rewardCoinsNumber;
    }

    public void setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    public ApplicationsAdapter(Context context, List<ApplicationResult.ApplicationInfo> listApplications, OnClickButtonsListener listener) {
        mListApp = listApplications;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mListener = listener;
    }

    public void setListApplications(List<ApplicationResult.ApplicationInfo> listApp) {
        mListApp = listApp;
    }

    @NonNull
    @Override
    public ApplicationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationsAdapter.ViewHolder holder, int position) {
        final ApplicationResult.ApplicationInfo applicationInfo = mListApp.get(position);

        Glide.with(mContext).load(applicationInfo.getLogo()).into(holder.ivIcon);
        holder.tvName.setText(applicationInfo.getName());

        String tvDescriptionContent = mContext.getString(R.string.download_app_to_get_coins).replace("{coins_num}", String.valueOf(rewardCoinsNumber));
        holder.tvDescription.setText(tvDescriptionContent);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDownload(applicationInfo.getPacketName());
            }
        });

        holder.btnReceiceCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickGetCoins(applicationInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListApp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivIcon;
        AppCompatTextView tvName;
        AppCompatTextView tvDescription;
        AppCompatButton btnDownload;
        AppCompatButton btnReceiceCoins;

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mListener.onClickItem(getAdapterPosition());
//                }
//            });
            ivIcon = itemView.findViewById(R.id.iv_app_icon);
            tvName = itemView.findViewById(R.id.tv_app_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            btnDownload = itemView.findViewById(R.id.btn_download);
            btnReceiceCoins = itemView.findViewById(R.id.btn_get_coins);
        }
    }

    public interface OnClickButtonsListener {
        void onClickDownload(String packetName);

        void onClickGetCoins(ApplicationResult.ApplicationInfo applicationInfo);
    }
}
