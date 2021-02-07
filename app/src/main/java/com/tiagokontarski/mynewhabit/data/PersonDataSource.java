package com.tiagokontarski.mynewhabit.data;

public interface PersonDataSource {
    boolean savePersonName(String personName);

    String getPersonName();
}
