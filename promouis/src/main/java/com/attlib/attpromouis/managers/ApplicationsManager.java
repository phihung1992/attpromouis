package com.attlib.attpromouis.managers;

import com.attlib.attpromouis.asynctasks.GetApplicationsAsyncTask;
import com.attlib.attpromouis.interfaces.IDownloadCallBack;
import com.attlib.attpromouis.models.ApplicationResult;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsManager {
    public void load(String apiURL, String userName, String password, IDownloadCallBack listener){
        new GetApplicationsAsyncTask(apiURL, userName, password, listener).execute();
    }
}
