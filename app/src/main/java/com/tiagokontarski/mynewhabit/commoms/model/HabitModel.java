package com.tiagokontarski.mynewhabit.commoms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

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

    @ColumnInfo
    public long createdAt;

    public HabitModel(String title, String duration, String hour, String minute, long createdAt) {
        this.title = title;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
        this.createdAt = createdAt;
    }

    @Ignore
    public HabitModel(int uid, String title, String duration, String hour, String minute, long createdAt) {
        this.uid = uid;
        this.title = title;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
        this.createdAt = createdAt;
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
