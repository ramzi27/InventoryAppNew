package com.ramzi.inventoryapp.networking;

import retrofit2.Retrofit;

/**
 * Created by user on 12/24/2017.
 */

public class RestService {
    public static BackupService getBackupService() {
        Retrofit retrofitIntance=RetrofitIntance.getInstance();
        BackupService APIService;
        APIService = retrofitIntance.create(BackupService.class);
        return APIService;
    }

    public static RestoreService getRestoreService() {
        Retrofit retrofitIntance = RetrofitIntance.getInstance();
        RestoreService APIService;
        APIService = retrofitIntance.create(RestoreService.class);
        return APIService;
    }


}
