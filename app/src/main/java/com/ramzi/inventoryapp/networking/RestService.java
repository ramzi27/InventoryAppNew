package com.ramzi.inventoryapp.networking;

import retrofit2.Retrofit;

/**
 * Created by user on 12/24/2017.
 */

public class RestService {
    public static APIService getAPIService(){
        Retrofit retrofitIntance=RetrofitIntance.getInstance();
       APIService APIService;
       APIService=retrofitIntance.create(APIService.class);
       return APIService;
    }
}
