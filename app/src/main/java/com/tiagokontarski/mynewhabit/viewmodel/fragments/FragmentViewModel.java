package com.tiagokontarski.mynewhabit.viewmodel.fragments;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.ui.HabitsActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class FragmentViewModel extends AndroidViewModel {

    private RoomDataSource roomDataSource;
    private DaysDataSource daysDataSource;
    private final WeakReference<Context> context;

    public FragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = new WeakReference<>(application.getBaseContext());
    }

    private final MutableLiveData<Boolean> mResponse = new MutableLiveData<>();
    public final LiveData<Boolean> response = mResponse;

    public void setDataSource(RoomDataSource roomDataSource, DaysDataSource daysDataSource) {
        this.roomDataSource = roomDataSource;
        this.daysDataSource = daysDataSource;
    }

    public List<HabitModel> updateList() {
        return roomDataSource.getAll();
    }

    public void getItem(int itemId) {
        HabitsActivity.launchItem(context.get(), itemId);
    }

    public void deleteItem(HabitModel model) {
        DaysModel daysModel = daysDataSource.get(model.getUid());
        daysDataSource.delete(daysModel);
        mResponse.postValue(roomDataSource.delete(model) > 0);
    }
}
