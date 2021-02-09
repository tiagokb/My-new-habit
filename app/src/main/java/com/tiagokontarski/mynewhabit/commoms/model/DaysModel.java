package com.tiagokontarski.mynewhabit.commoms.model;

import android.text.BoringLayout;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "days_of_week")
public class DaysModel {

    @ColumnInfo
    @PrimaryKey
    int id;

    @ColumnInfo
    Boolean sunday;

    @ColumnInfo
    Boolean monday;

    @ColumnInfo
    Boolean tuesday;

    @ColumnInfo
    Boolean wednesday;

    @ColumnInfo
    Boolean thursday;

    @ColumnInfo
    Boolean friday;

    @ColumnInfo
    Boolean saturnday;

    public DaysModel(int id, Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturnday) {
        this.id = id;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturnday = saturnday;
    }

    @Ignore
    public DaysModel(Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturnday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturnday = saturnday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public Boolean getSaturnday() {
        return saturnday;
    }
}
