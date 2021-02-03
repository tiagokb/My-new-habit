package com.tiagokontarski.mynewhabit.data;

public interface DataSource {
    boolean savePersonName(String personName);

    String getPersonName();
}
