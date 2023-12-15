package com.attlib.attpromouis.asynctasks;

import android.os.AsyncTask;

import com.attlib.attpromouis.interfaces.IApplicationService;
import com.attlib.attpromouis.interfaces.IDownloadCallBack;
import com.attlib.attpromouis.managers.AppsRetrofitClient;
import com.attlib.attpromouis.models.ApplicationResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetApplicationsAsyncTask extends AsyncTask<Void, Integer, Void> {
    private IDownloadCallBack listener;
    private String apiUrl, userName, password;

    public GetApplicationsAsyncTask(String apiURL, String userName, String password, IDownloadCallBack listener) {
        this.listener = listener;
        this.apiUrl = apiURL;
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        listener.onStartQuery();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        IApplicationService service = AppsRetrofitClient.createClient(apiUrl, userName, password).create(IApplicationService.class);
        service.getApplicationsInfo().enqueue(new Callback<ApplicationResult>() {
            @Override
            public void onResponse(Call<ApplicationResult> call, Response<ApplicationResult> response) {
                if (response.code() != 200) {
                    listener.onFailure("Error code: " + response.code());
                    return;
                }

                ApplicationResult responseResult = response.body();
                if (responseResult == null) {
                    return;
                }

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApplicationResult> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
        return null;
    }
}
