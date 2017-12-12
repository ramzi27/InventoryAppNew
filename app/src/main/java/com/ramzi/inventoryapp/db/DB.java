package com.ramzi.inventoryapp.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class DB {
    private static Context c;
    private static DBHandler dbHandler;

    public static DBHandler getDB(Context context) {
        c = context;
        if (dbHandler == null) {
            Migration migration = new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                }
            };
            dbHandler = Room.databaseBuilder(c, DBHandler.class, "myDataBase").allowMainThreadQueries().addMigrations(migration).build();
            return dbHandler;
        }
        return dbHandler;
    }

}