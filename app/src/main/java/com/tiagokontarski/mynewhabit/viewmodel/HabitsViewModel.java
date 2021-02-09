package com.tiagokontarski.mynewhabit.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.broadcast.NotificationManager;
import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;
import com.tiagokontarski.mynewhabit.commoms.utils.DaysOfWeek;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.KeysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;

import java.util.List;

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

    private final MutableLiveData<Boolean> mFinalResponse = new MutableLiveData<>();
    public final LiveData<Boolean> finalResponse = mFinalResponse;

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

    public void setNotification(Context context,
                                NotificationManager manager,
                                HabitModel model,
                                DaysModel daysModel,
                                KeysDataSource keysDataSource,
                                Boolean isEditingMode) {

        int branch = model.getUid();

        if (isEditingMode) {
            List<Integer> keys = keysDataSource.getKeys(branch);
            manager.dropNotification(keys);

            List<NotificationKeysModel> keysModels = keysDataSource.getBranch(branch);
            for (NotificationKeysModel keysModel : keysModels) {
                keysDataSource.delete(keysModel);
            }
        }

        DaysOfWeek daysOfWeek = new DaysOfWeek();
        List<Integer> days = daysOfWeek.getSelectedDays(daysModel);

        int hour = Integer.parseInt(model.getHour());
        int minute = Integer.parseInt(model.getMinute());
        String messageTitle = model.getTitle();
        String message = context.getString(R.string.notification_content, model.getDuration());

        for (int day : days) {
            int key = (int) System.nanoTime();
            NotificationKeysModel keysModel = new NotificationKeysModel(branch, key, day, System.currentTimeMillis());

            if (keysDataSource.insert(keysModel) > 0) {
                if (manager.createNotification(day, hour, minute, key, messageTitle, message) <= 0) {

                    List<Integer> keys = keysDataSource.getKeys(branch);
                    manager.dropNotification(keys);

                    List<NotificationKeysModel> keysToDelete = keysDataSource.getBranch(branch);
                    for (NotificationKeysModel keyToDelete : keysToDelete) {
                        keysDataSource.delete(keyToDelete);
                    }

                    DaysModel daysToDelete = daysDataSource.get(branch);
                    daysDataSource.delete(daysToDelete);

                    roomDataSource.delete(model);
                    mFinalResponse.postValue(false);
                    return;
                }
            }
        }

        mFinalResponse.postValue(true);
    }

}
