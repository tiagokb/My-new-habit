package com.tiagokontarski.mynewhabit.data;

import android.content.Context;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.data.room.main.Dao;
import com.tiagokontarski.mynewhabit.data.room.main.RoomDataBase;

import java.util.List;

public class RoomDataSource {

    private final Dao dataSource;

    public RoomDataSource(Context context) {
        dataSource = RoomDataBase.getInstance(context).getDao();
    }

    //Create
    public long insert(HabitModel model) {
        return dataSource.insert(model);
    }

    //Read
    public List<HabitModel> getAll() {
        return dataSource.getAll();
    }

    public HabitModel getHabit(int id) {
        return dataSource.getHabit(id);
    }

    //Update
    public int update(HabitModel model) {
        return dataSource.update(model);
    }

    //Delete
    public int delete(HabitModel model) {
        return dataSource.delete(model);
    }

    public HabitModel getNewestModel() {
        return dataSource.getNewestModel();
    }
}
