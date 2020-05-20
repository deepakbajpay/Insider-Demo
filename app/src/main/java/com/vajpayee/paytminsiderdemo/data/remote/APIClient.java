package com.vajpayee.paytminsiderdemo.data.remote;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mehul on 11-01-2017.
 */
public class APIClient {
    public static final String BASE_URL = "https://api.insider.in/";
    private static Retrofit retrofit = null;
    private WebServices webServices;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "Accept: application/json"); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return builder.readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS)
                .build();

    }

    public WebServices getWebServices() {
        if (webServices == null) {
            webServices = getClient().create(WebServices.class);
        }
        return webServices;
    }
}
