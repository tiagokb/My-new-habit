package com.tiagokontarski.mynewhabit.commoms.utils;

import android.util.Log;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;

import java.util.ArrayList;
import java.util.List;

public class DaysOfWeek {
    int sunday = 1;
    int monday = 2;
    int tuesday = 3;
    int wednesday = 4;
    int thursday = 5;
    int friday = 6;
    int saturday = 7;

    public List<Integer> getSelectedDays(DaysModel model) {
        List<Integer> selectedDays = new ArrayList<>();

        if (model.getSunday()) {
            selectedDays.add(getSunday());
        }

        if (model.getMonday()) {
            selectedDays.add(getMonday());
        }

        if (model.getTuesday()) {
            selectedDays.add(getTuesday());
        }

        if (model.getWednesday()) {
            selectedDays.add(getWednesday());
        }

        if (model.getThursday()) {
            selectedDays.add(getThursday());
        }

        if (model.getFriday()) {
            selectedDays.add(getFriday());
        }

        if (model.getSaturnday()) {
            selectedDays.add(getSaturday());
        }

        for (int day : selectedDays) {
            Log.i("daysOfWeek", String.valueOf(day));
        }
        return selectedDays;
    }

    public int getSunday() {
        return sunday;
    }

    public int getMonday() {
        return monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public int getFriday() {
        return friday;
    }

    public int getSaturday() {
        return saturday;
    }
}
