package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Ramzi on 26-Dec-17.
 */

public class DateConvertor {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
