package com.tiagokontarski.mynewhabit.commoms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "keys")
public class NotificationKeysModel {

    @PrimaryKey
    public int key;

    @ColumnInfo
    public int id;

    @ColumnInfo
    public long createdAt;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getKey() {
        return key;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
