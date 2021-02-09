package com.tiagokontarski.mynewhabit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;

public class HabitsViewModel extends AndroidViewModel {
    public HabitsViewModel(@NonNull Application application) {
        super(application);
    }

    private RoomDataSource roomDataSource;
    private DaysDataSource daysDataSource;

    public void setDataSource(RoomDataSource roomDataSource, DaysDataSource daysDataSource) {
        this.roomDataSource = roomDataSource;
        this.daysDataSource = daysDataSource;
    }

    private final MutableLiveData<Boolean> mResponse = new MutableLiveData<>();
    public final LiveData<Boolean> response = mResponse;

    private final MutableLiveData<HabitModel> mItem = new MutableLiveData<>();
    public final LiveData<HabitModel> item = mItem;

    public void save(String description, String duraion, String hour, String minute, DaysModel daysOfWeek) {
        boolean response = roomDataSource.insert(new HabitModel(description, duraion, hour, minute, System.currentTimeMillis())) > 0;
        if (response) {
            int id = roomDataSource.getNewestModel().getUid();
            daysOfWeek.setId(id);
            response = daysDataSource.insert(daysOfWeek) > 0;
        }
        mResponse.postValue(response);
    }

    public void update(int id, String description, String duraion, String hour, String minute, DaysModel daysOfWeek) {
        boolean response = roomDataSource.update(new HabitModel(id, description, duraion, hour, minute, System.currentTimeMillis())) > 0;
        if (response) {
            response = daysDataSource.update(daysOfWeek) > 0;
        }
        mResponse.postValue(response);
    }

    public void setActualItem(int id) {
        mItem.postValue(roomDataSource.getHabit(id));
    }

    public HabitModel getNewestModel() {
        return roomDataSource.getNewestModel();
    }

    public DaysModel getDaysOfWeek(int id) {
        return daysDataSource.get(id);
    }
}
