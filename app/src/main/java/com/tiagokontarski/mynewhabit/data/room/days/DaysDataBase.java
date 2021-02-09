package com.tiagokontarski.mynewhabit.data.room.days;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;

@Database(entities = {DaysModel.class}, version = 1)
public abstract class DaysDataBase extends RoomDatabase {
    private static final String DAYS_DB_NAME = "days_db";

    public abstract DaysDao getDao();

    private static DaysDataBase INSTANCE;

    public static DaysDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DaysDataBase.class, DAYS_DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

}
