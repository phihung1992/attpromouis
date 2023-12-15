package com.attlib.attpromouis.interfaces;

import com.attlib.attpromouis.models.ApplicationResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApplicationService {
    @GET("api/Application/applicationsNew")
    Call<ApplicationResult> getApplicationsInfo();
}
