package com.tiagokontarski.mynewhabit.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PersonSharedPreferences {

    private static final String SHARED_NAME = "person_shared";
    private static final String KEY_PERSON_NAME = "key_person_key";
    private final Context context;
    private static SharedPreferences local;

    private PersonSharedPreferences(Context context) {
        this.context = context;
    }

    public static PersonSharedPreferences getInstance(Context context) {
        PersonSharedPreferences preferences = new PersonSharedPreferences(context);

        if (local == null) {
            local = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        }

        return preferences;
    }

    boolean savePersonName(String personName) {
        final SharedPreferences.Editor mLocal = local.edit();
        mLocal.clear();
        mLocal.putString(KEY_PERSON_NAME, personName);
        return mLocal.commit();
    }

    String getPersonName() {
        return local.getString(KEY_PERSON_NAME, "");
    }
}
