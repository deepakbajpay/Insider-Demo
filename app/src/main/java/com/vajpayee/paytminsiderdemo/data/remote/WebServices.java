package com.vajpayee.paytminsiderdemo.data.remote;

import android.content.Context;

import com.vajpayee.paytminsiderdemo.ui.dashboard.DashboardContract;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebServices {

    void getEvents(Context context, String apiUrl, FetchDataListener listener);

}
