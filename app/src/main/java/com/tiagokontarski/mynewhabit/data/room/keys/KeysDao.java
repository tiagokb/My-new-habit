package com.tiagokontarski.mynewhabit.data.room.keys;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tiagokontarski.mynewhabit.broadcast.NotificationManager;
import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;

import java.util.List;

@Dao
public interface KeysDao {

    //create
    @Insert
    long insert(NotificationKeysModel model);

    //read
    @Query("select `key` from notification_keys where branch = :branch")
    List<Integer> getKeys(int branch);

    @Query("select * from notification_keys order by createdAt desc limit 1")
    NotificationKeysModel getLastInserted();

    @Query("select * from notification_keys where branch = :id")
    List<NotificationKeysModel> getBranch(int id);

    //update
    @Update
    int update(NotificationKeysModel model);

    //delete
    @Delete
    int delete(NotificationKeysModel model);
}
