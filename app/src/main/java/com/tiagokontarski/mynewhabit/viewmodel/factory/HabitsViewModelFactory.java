package com.tiagokontarski.mynewhabit.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tiagokontarski.mynewhabit.viewmodel.HabitsViewModel;

public class HabitsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public HabitsViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HabitsViewModel.class)) {
            return (T) new HabitsViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
