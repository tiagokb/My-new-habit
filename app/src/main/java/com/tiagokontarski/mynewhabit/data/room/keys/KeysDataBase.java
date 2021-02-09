package com.tiagokontarski.mynewhabit.data.room.keys;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;

@Database(entities = {NotificationKeysModel.class}, version = 1)
public abstract class KeysDataBase extends RoomDatabase {
    private static final String KEYS_DB_NAME = "keys_db";

    public abstract KeysDao getDao();

    private static KeysDataBase INSTANCE;

    public static KeysDataBase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, KeysDataBase.class, KEYS_DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
