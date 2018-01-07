package com.ramzi.inventoryapp.networking;

import retrofit2.Retrofit;


/**
 * The type Rest service.
 */
public class RestService {
    /**
     * Gets backup service.
     *
     * @return the backup service
     */
    public static BackupService getBackupService() {
        Retrofit retrofitIntance=RetrofitIntance.getInstance();
        BackupService APIService;
        APIService = retrofitIntance.create(BackupService.class);
        return APIService;
    }

    /**
     * Gets restore service.
     *
     * @return the restore service
     */
    public static RestoreService getRestoreService() {
        Retrofit retrofitIntance = RetrofitIntance.getInstance();
        RestoreService APIService;
        APIService = retrofitIntance.create(RestoreService.class);
        return APIService;
    }


}
