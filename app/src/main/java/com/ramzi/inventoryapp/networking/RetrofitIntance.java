package com.ramzi.inventoryapp.networking;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by user on 12/24/2017.
 */

public class RetrofitIntance {
    private static Retrofit retrofit;
    private final static String BACKUP_URL="http://";

    private RetrofitIntance(){}

    public static Retrofit getInstance(){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(BACKUP_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build();
        }
        return retrofit;
    }
}
