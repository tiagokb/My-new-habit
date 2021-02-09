package com.tiagokontarski.mynewhabit.commoms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_keys")
public class NotificationKeysModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public int branch;

    @ColumnInfo
    public int key;

    @ColumnInfo
    public int day;

    @ColumnInfo
    public long createdAt;

    public NotificationKeysModel(int branch, int key, int day, long createdAt) {
        this.branch = branch;
        this.key = key;
        this.day = day;
        this.createdAt = createdAt;
    }

    public int getUid() {
        return uid;
    }

    public int getBranch() {
        return branch;
    }

    public int getKeys() {
        return key;
    }

    public int getDay() {
        return day;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
