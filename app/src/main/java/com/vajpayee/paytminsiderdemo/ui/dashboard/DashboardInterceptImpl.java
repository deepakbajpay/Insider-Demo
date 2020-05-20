package com.vajpayee.paytminsiderdemo.ui.dashboard;

import android.content.Context;
import android.util.Log;

import com.vajpayee.paytminsiderdemo.data.remote.FetchDataListener;
import com.vajpayee.paytminsiderdemo.data.remote.WebServicesImpl;

import org.json.JSONObject;

public class DashboardInterceptImpl implements DashboardContract.DashboardInterceptor, FetchDataListener {
    OnResultListener resultListener;

    @Override
    public void getEventsFromApi(Context context, String city, String norm, String filterBy, final OnResultListener onResultListener) {

        this.resultListener = onResultListener;
        new WebServicesImpl().getEvents(context, "home?norm=1&filterBy=go-out&city=" + city, this);

    /*    new APIClient().getWebServices().getEvents(norm, filterBy, city).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 400) {
                    onResultListener.onFailure("UnAuthorize to request");
                    Log.e("getEventsFromApi", response.toString());
                } else if (response.code() == 404) {
                    onResultListener.onFailure("Web address not found");
                    Log.e("getEventsFromApi", response.toString());
                } else {
                    try {

                        System.out.println("DashboardInterceptImpl.onResponse " + response.body().string());
                        if (response.body() != null)
                            onResultListener.onSuccess(new JSONObject(response.body().string()));
                        else
                            onResultListener.onFailure("Something went wrong");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onResultListener.onFailure(t.getLocalizedMessage());
                t.printStackTrace();
            }
        });*/
    }

    @Override
    public void onFetchComplete(JSONObject data) {
        System.out.println("DashboardInterceptImpl.onFetchComplete " + data);
        resultListener.onSuccess(data);
    }

    @Override
    public void onFetchFailure(String msg) {
        resultListener.onFailure(msg);
    }

    @Override
    public void onFetchStart() {

    }
}
