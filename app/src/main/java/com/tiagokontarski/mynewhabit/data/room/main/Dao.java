package com.tiagokontarski.mynewhabit.data.room.main;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    //Create
    @Insert
    long insert(HabitModel model);

    //Read
    @Query("SELECT * FROM habits")
    List<HabitModel> getAll();

    @Query("SELECT * FROM habits WHERE uid = :id")
    HabitModel getHabit(int id);

    @Query("SELECT * FROM habits ORDER BY createdAt DESC LIMIT 1")
    HabitModel getNewestModel();

    //Update
    @Update
    int update(HabitModel model);

    //Delete
    @Delete
    int delete(HabitModel model);
}
