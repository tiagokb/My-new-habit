package com.tiagokontarski.mynewhabit.data.room.days;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;

import java.util.List;

@Dao
public interface DaysDao {

    //Create
    @Insert
    long insert(DaysModel model);

    //Read
    @Query("select * from days_of_week where id = :id")
    DaysModel getSelected(int id);

    //Update
    @Update
    int update(DaysModel model);

    //Delete
    @Delete
    int delete(DaysModel model);

}
