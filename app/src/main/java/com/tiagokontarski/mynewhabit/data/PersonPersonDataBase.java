package com.tiagokontarski.mynewhabit.data;

import android.content.Context;

public class PersonPersonDataBase implements PersonDataSource {

    private final Context context;

    public PersonPersonDataBase(Context context) {
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
