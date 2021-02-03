package com.tiagokontarski.mynewhabit.data;

import android.content.Context;
import android.security.identity.PersonalizationData;

public class LocalDataSource implements DataSource {

    private final Context context;

    public LocalDataSource(Context context) {
        this.context = context;
    }

    @Override
    public boolean savePersonName(String personName) {
        return PersonSharedPreferences.getInstance(context).savePersonName(personName);
    }

    @Override
    public String getPersonName() {
        return PersonSharedPreferences.getInstance(context).getPersonName();
    }
}
