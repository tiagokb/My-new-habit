package com.tiagokontarski.mynewhabit.data.room.main;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

@Database(entities = {HabitModel.class}, version = 1)
public abstract class RoomDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "room_db";

    public abstract Dao getDao();

    private static RoomDataBase INSTANCE;

    public static RoomDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RoomDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

}
