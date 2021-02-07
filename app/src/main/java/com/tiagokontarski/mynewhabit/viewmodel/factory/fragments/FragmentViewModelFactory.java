package com.tiagokontarski.mynewhabit.viewmodel.factory.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tiagokontarski.mynewhabit.viewmodel.fragments.FragmentViewModel;

public class FragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public FragmentViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FragmentViewModel.class)) {
            return (T) new FragmentViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
