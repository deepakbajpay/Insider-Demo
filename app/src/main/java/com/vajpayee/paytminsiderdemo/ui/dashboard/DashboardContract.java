package com.vajpayee.paytminsiderdemo.ui.dashboard;

import android.content.Context;

import com.vajpayee.paytminsiderdemo.data.remote.FetchDataListener;

import org.json.JSONObject;

public interface DashboardContract {
    interface View{
        void showMessage(String message);
        void populateEvents(JSONObject jsonObject);
        void showLoading();
        void hideLoading();
    }

    interface Presenter{
        void getEvents(String city);
    }

    interface DashboardInterceptor{
        void getEventsFromApi(Context context,String city, String norm, String filterBy, OnResultListener onResultListener);

        interface OnResultListener {
            void onFailure(String message);
            void onSuccess(JSONObject data);
        }
    }


}
