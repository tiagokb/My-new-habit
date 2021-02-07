package com.tiagokontarski.mynewhabit.data.room;

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

    //Update
    @Update
    int update(HabitModel model);

    //Delete
    @Delete
    int delete(HabitModel model);

}
