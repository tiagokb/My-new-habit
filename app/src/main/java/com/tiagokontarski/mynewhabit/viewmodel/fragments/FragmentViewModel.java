package com.tiagokontarski.mynewhabit.viewmodel.fragments;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.broadcast.NotificationManager;
import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.KeysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.ui.HabitsActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class FragmentViewModel extends AndroidViewModel {

    private RoomDataSource roomDataSource;
    private DaysDataSource daysDataSource;
    private KeysDataSource keysDataSource;
    private final WeakReference<Context> context;
    private NotificationManager manager;

    public FragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = new WeakReference<>(application.getBaseContext());
    }

    private final MutableLiveData<Boolean> mResponse = new MutableLiveData<>();
    public final LiveData<Boolean> response = mResponse;

    public void setDataSource(RoomDataSource roomDataSource, DaysDataSource daysDataSource, KeysDataSource keysDataSource, NotificationManager manager) {
        this.roomDataSource = roomDataSource;
        this.daysDataSource = daysDataSource;
        this.keysDataSource = keysDataSource;
        this.manager = manager;
    }

    public List<HabitModel> updateList() {
        return roomDataSource.getAll();
    }

    public void getItem(int itemId) {
        HabitsActivity.launchItem(context.get(), itemId);
    }

    public void deleteItem(HabitModel model) {
        int id = model.getUid();

        List<Integer> keys = keysDataSource.getKeys(id);
        manager.dropNotification(keys);

        List<NotificationKeysModel> keysModels = keysDataSource.getBranch(id);
        for (NotificationKeysModel keysModel : keysModels) {
            keysDataSource.delete(keysModel);
        }

        DaysModel daysModel = daysDataSource.get(id);
        daysDataSource.delete(daysModel);

        mResponse.postValue(roomDataSource.delete(model) > 0);
    }
}
