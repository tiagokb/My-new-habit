package com.tiagokontarski.mynewhabit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tiagokontarski.mynewhabit.data.DataSource;

public class WelcomeViewModel extends AndroidViewModel {

    public WelcomeViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<Boolean> mDataResponse = new MutableLiveData<>();
    public final LiveData<Boolean> dataResponse = mDataResponse;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void savePersonName(String personName) {
        mDataResponse.setValue(dataSource.savePersonName(personName));
    }

    public boolean verifyUserCreated() {
        return !dataSource.getPersonName().equals("");
    }
}
