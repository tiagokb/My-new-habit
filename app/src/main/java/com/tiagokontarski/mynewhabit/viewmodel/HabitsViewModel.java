package com.tiagokontarski.mynewhabit.viewmodel;

import android.app.Application;
import android.graphics.ColorSpace;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;

import java.util.Calendar;

public class HabitsViewModel extends AndroidViewModel {
    public HabitsViewModel(@NonNull Application application) {
        super(application);
    }

    private RoomDataSource dataSource;

    public void setDataSource(RoomDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final MutableLiveData<Boolean> mResponse = new MutableLiveData<>();
    public final LiveData<Boolean> response = mResponse;

    private final MutableLiveData<HabitModel> mItem = new MutableLiveData<>();
    public final LiveData<HabitModel> item = mItem;

    public void save(String description, String duraion, String hour, String minute) {
        boolean response = dataSource.insert(new HabitModel(description, duraion, hour, minute)) > 0;
        mResponse.postValue(response);
    }

    public void update(int id, String description, String duraion, String hour, String minute) {
        boolean response = dataSource.update(new HabitModel(id, description, duraion, hour, minute)) > 0;
        mResponse.postValue(response);
    }

    public void setActualItem(int id) {
        mItem.postValue(dataSource.getHabit(id));
    }
}
