package com.ramzi.inventoryapp.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Ramzi on 26-Dec-17.
 */
public class DateConvertor {

    /**
     * To date date.
     *
     * @param value the value
     * @return the date
     */
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * To long long.
     *
     * @param value the value
     * @return the long
     */
    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
