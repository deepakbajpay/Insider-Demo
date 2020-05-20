package com.vajpayee.paytminsiderdemo.data.remote;


import org.json.JSONObject;

public interface FetchDataListener {
    void onFetchComplete(JSONObject data);

    void onFetchFailure(String msg);

    void onFetchStart();
}