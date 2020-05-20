package com.vajpayee.paytminsiderdemo.data.remote;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vajpayee.paytminsiderdemo.ui.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class WebServicesImpl implements WebServices {
    @Override
    public void getEvents(Context context, String apiUrl, FetchDataListener listener) {
        try {
            getRequest(context, listener, apiUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onFetchFailure("Something went wrong");
        }
    }

    private void postRequest(final Context context, final FetchDataListener listener, JSONObject params, final String ApiURL) throws JSONException {
        if (listener != null) {
            //call onFetchComplete of the listener
            //to show progress dialog
            //-indicates calling started
            listener.onFetchStart();
        }
        //base server URL

        String baseUrl = Constants.BASE_URL;
        //add extension api url received from caller
        //and make full api
        String url = baseUrl + ApiURL;
        //Requesting with post body as params
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (listener != null) {
                                if (response.has("response")) {
                                    //received response
                                    //call onFetchComplete of the listener
                                    listener.onFetchComplete(response);
                                } else if (response.has("error")) {
                                    //has error in response
                                    //call onFetchFailure of the listener
                                    listener.onFetchFailure(response.getString("error"));
                                } else {
                                    listener.onFetchComplete(null);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    listener.onFetchFailure("Network Connectivity Problem");
                } else if (error.networkResponse != null && error.networkResponse.data != null) {
                    VolleyError volley_error = new VolleyError(new String(error.networkResponse.data));
                    String errorMessage = "";
                    try {
                        JSONObject errorJson = new JSONObject(volley_error.getMessage().toString());
                        if (errorJson.has("error")) errorMessage = errorJson.getString("error");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (errorMessage.isEmpty()) {
                        errorMessage = volley_error.getMessage();
                    }

                    if (listener != null) listener.onFetchFailure(errorMessage);
                } else {
                    listener.onFetchFailure("Something went wrong. Please try again later");
                }

            }
        });

        RequestQueueService.getInstance(context).addToRequestQueue(postRequest.setShouldCache(false));
    }

    private void getRequest(final Context context, final FetchDataListener listener, final String ApiURL) throws JSONException {
        if (listener != null) {
            //call onFetchComplete of the listener
            //to show progress dialog
            //-indicates calling started
            listener.onFetchStart();
        }
        //base server URL
        String baseUrl = Constants.BASE_URL;
        //add extension api url received from caller
        //and make full api
        String url = baseUrl + ApiURL;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onFetchComplete(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    if (listener != null) listener.onFetchFailure("Network Connectivity Problem");
                } else if (error.networkResponse != null && error.networkResponse.data != null) {
                    VolleyError volleyError = new VolleyError(new String(error.networkResponse.data));
                    String errorMessage = "";
                    try {
                        JSONObject errorJson = new JSONObject(volleyError.getMessage());
                        if (errorJson.has("error")) errorMessage = errorJson.getString("error");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (errorMessage.isEmpty()) {
                        errorMessage = volleyError.getMessage();
                    }

                    if (listener != null) listener.onFetchFailure(errorMessage);
                } else {
                    if (listener != null)
                        listener.onFetchFailure("Something went wrong. Please try again later");
                }

            }
        });

        RequestQueueService.getInstance(context).addToRequestQueue(postRequest.setShouldCache(false));
    }
}
