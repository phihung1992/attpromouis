package com.attlib.attpromouis.views;

import static com.attlib.attpromouis.Constants.SHARED_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attlib.attpromouis.Constants;
import com.attlib.attpromouis.R;
import com.attlib.attpromouis.interfaces.IDownloadCallBack;
import com.attlib.attpromouis.interfaces.IInstalledCheckCallback;
import com.attlib.attpromouis.managers.ApplicationsManager;
import com.attlib.attpromouis.models.ApplicationResult;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsView extends LinearLayout {
    private ApplicationsManager mApplicationManager;
    private ApplicationsAdapter mApplicationsAdapter;

    private Context mContext;
    private RecyclerView mRcvApplications;
    private AppCompatTextView mTvTitle;
    private int rewardCoins;
    private List<ApplicationResult.ApplicationInfo> mListApplicationsInfo;
    private IInstalledCheckCallback installedCheckCallback;

    public ApplicationsView(Context context) {
        super(context);
        initViews(context);
    }

    public ApplicationsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ApplicationsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public ApplicationsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        View.inflate(context, R.layout.applications_view, this);

        mRcvApplications = findViewById(R.id.rcv_applications);
        mRcvApplications.setLayoutManager(new GridLayoutManager(context, 1));

        mTvTitle = findViewById(R.id.tv_title);
    }

    public AppCompatTextView getTitle() {
        return mTvTitle;
    }

    public void loadData() {
        mApplicationManager = new ApplicationsManager();
        mApplicationManager.load(Constants.BASE_SERVER, Constants.USER_NAME, Constants.PASSWORD, new IDownloadCallBack() {
            @Override
            public void onStartQuery() {

            }

            @Override
            public void onSuccess(Object successData) {
                mListApplicationsInfo = (List<ApplicationResult.ApplicationInfo>) successData;
                removeInstalledApp();
                mApplicationsAdapter = new ApplicationsAdapter(mContext, mListApplicationsInfo, onClickItemButtonsListener);
                mApplicationsAdapter.setRewardCoinsNumber(rewardCoins);
                mRcvApplications.setAdapter(mApplicationsAdapter);
                //dismissLoading();
            }

            @Override
            public void onFailure(String failMessage) {
                Log.e("ApplicationsView", failMessage);
            }
        });
    }

    public void setRewardCoins(int rewardCoins) {
        this.rewardCoins = rewardCoins;
    }

    private ApplicationsAdapter.OnClickButtonsListener onClickItemButtonsListener = new ApplicationsAdapter.OnClickButtonsListener() {
        @Override
        public void onClickDownload(String packetName) {
//            Sound.click();
            goToMarketApp(packetName);
        }

        @Override
        public void onClickGetCoins(ApplicationResult.ApplicationInfo applicationInfo) {
            doClickGetCoinsButton(applicationInfo.getPacketName(), applicationInfo.getKey());

            removeInstalledApp();
            mApplicationsAdapter.setListApplications(mListApplicationsInfo);
            mApplicationsAdapter.notifyDataSetChanged();
        }
    };

    private void goToMarketApp(String packetName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packetName));
        mContext.startActivity(intent);
    }

    private void doClickGetCoinsButton(String packetName, String preferenceKey) {
        if (checkAppInstalledOrNot(packetName)) {
            String toastContent = mContext.getString(R.string.downloaded_app_coins_message).replace("{coins_num}", String.valueOf(rewardCoins));
            Toast.makeText(mContext, toastContent, Toast.LENGTH_SHORT).show();
            if (installedCheckCallback != null) installedCheckCallback.onChecked(true);
            putPreferenceBoolean(preferenceKey, true);
        } else {
            Toast.makeText(mContext, R.string.install_before_get_coin, Toast.LENGTH_SHORT).show();
            if (installedCheckCallback != null) installedCheckCallback.onChecked(false);
        }
    }

    private boolean checkAppInstalledOrNot(String packageName) {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void removeInstalledApp() {
        if (mListApplicationsInfo == null) return;
        String packageName = mContext.getPackageName();

        List<ApplicationResult.ApplicationInfo> list = new ArrayList<>();
        for (ApplicationResult.ApplicationInfo item : mListApplicationsInfo) {
            if (packageName.equals(item.getPacketName()) || getPreferenceBoolean(item.getKey(), false)) {
                // Nếu item chính là app hiện tại, hoặc đã được cài rồi thì không thêm vào list
                continue;
            }
            list.add(item);
        }

        mListApplicationsInfo = list;
    }

    public boolean getPreferenceBoolean(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putPreferenceBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void setInstalledCheckCallback(IInstalledCheckCallback callback) {
        this.installedCheckCallback = callback;
    }
}
