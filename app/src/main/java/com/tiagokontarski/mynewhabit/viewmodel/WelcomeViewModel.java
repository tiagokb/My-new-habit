package com.tiagokontarski.mynewhabit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.data.PersonDataSource;

public class WelcomeViewModel extends AndroidViewModel {

    public WelcomeViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<Boolean> mDataResponse = new MutableLiveData<>();
    public final LiveData<Boolean> dataResponse = mDataResponse;

    private PersonDataSource personDataSource;

    public void setPersonDataSource(PersonDataSource personDataSource) {
        this.personDataSource = personDataSource;
    }

    public void savePersonName(String personName) {
        mDataResponse.setValue(personDataSource.savePersonName(personName));
    }

    public boolean verifyUserCreated() {
        return !personDataSource.getPersonName().equals("");
    }
}
