package com.attsolution.promouis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.attlib.attpromouis.views.ApplicationsView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ApplicationsView applicationsView = findViewById(R.id.av_applications);
        applicationsView.setRewardCoins(300);
        applicationsView.loadData();
    }
}
