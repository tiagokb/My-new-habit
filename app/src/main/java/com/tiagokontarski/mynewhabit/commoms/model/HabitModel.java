package com.tiagokontarski.mynewhabit.commoms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "habits")
public class HabitModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    String title;

    @ColumnInfo
    String duration;

    @ColumnInfo
    String hour;

    @ColumnInfo
    String minute;

    public HabitModel(String title, String duration, String hour, String minute) {
        this.title = title;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
    }

    public HabitModel(int id, String title, String duration, String hour, String minute) {
        this.uid = id;
        this.title = title;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public int getUid() {
        return uid;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }
}
