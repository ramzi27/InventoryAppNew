package com.ramzi.inventoryapp.networking;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The type Retrofit intance.
 */
public class RetrofitIntance {
    private final static String BACKUP_URL = "http://10.0.2.2:8080/InventoryAppBackend/";
    private static Retrofit retrofit;

    private RetrofitIntance(){}

    /**
     * Get instance retrofit.
     *
     * @return the retrofit
     */
    public static Retrofit getInstance(){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(BACKUP_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
