package com.tiagokontarski.mynewhabit.viewmodel.factory;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tiagokontarski.mynewhabit.viewmodel.WelcomeViewModel;

public class WelcomeViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public WelcomeViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WelcomeViewModel.class)) {
            return (T) new WelcomeViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
