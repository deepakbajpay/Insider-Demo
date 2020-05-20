package com.vajpayee.paytminsiderdemo.ui.dashboard;

import android.content.Context;

import org.json.JSONObject;

public class DashboardPresenterImpl implements DashboardContract.Presenter, DashboardContract.DashboardInterceptor.OnResultListener {

    private DashboardContract.DashboardInterceptor interceptor;
    private DashboardContract.View view;
    private Context context;

    public DashboardPresenterImpl(Context context,DashboardContract.DashboardInterceptor interceptor, DashboardContract.View view) {
        this.interceptor = interceptor;
        this.view = view;
        this.context = context;
    }

    @Override
    public void getEvents(String city) {
        view.showLoading();
        interceptor.getEventsFromApi(context,city,"1", "go-out",  this);
    }

    @Override
    public void onFailure(String message) {
        view.hideLoading();
        view.showMessage(message);
    }

    @Override
    public void onSuccess(JSONObject data) {
        view.hideLoading();
        view.populateEvents(data);
    }
}
