package com.tiagokontarski.mynewhabit.viewmodel.fragments;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.commoms.recyclerview.HabitAdapter;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.ui.HabitsActivity;
import com.tiagokontarski.mynewhabit.ui.fragments.HabitFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class FragmentViewModel extends AndroidViewModel {

    private RoomDataSource dataSource;
    private final WeakReference<Context> context;

    public FragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = new WeakReference<>(application.getBaseContext());
    }

    private final MutableLiveData<Boolean> mResponse = new MutableLiveData<>();
    public final LiveData<Boolean> response = mResponse;

    public void setDataSource(RoomDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<HabitModel> updateList() {
        return dataSource.getAll();
    }

    public void getItem(int itemId) {
        HabitsActivity.launchItem(context.get(), itemId);
    }

    public void deleteItem(HabitModel model) {
        mResponse.postValue(dataSource.delete(model) > 0);
    }
}
