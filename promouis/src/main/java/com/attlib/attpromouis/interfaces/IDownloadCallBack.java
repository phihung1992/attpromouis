package com.attlib.attpromouis.interfaces;

public interface IDownloadCallBack {
    void onStartQuery();

    void onSuccess(Object successData);

    void onFailure(String failMessage);
}
