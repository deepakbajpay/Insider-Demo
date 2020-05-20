package com.vajpayee.paytminsiderdemo.data.remote;

import android.content.Context;

public interface WebServices {

    void getEvents(Context context, String apiUrl, FetchDataListener listener);

}
