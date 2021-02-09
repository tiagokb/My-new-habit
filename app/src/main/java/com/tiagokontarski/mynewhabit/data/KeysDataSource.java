package com.tiagokontarski.mynewhabit.data;

import android.content.Context;

import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;
import com.tiagokontarski.mynewhabit.data.room.keys.KeysDao;
import com.tiagokontarski.mynewhabit.data.room.keys.KeysDataBase;

import java.util.List;

public class KeysDataSource {

    private KeysDao dataSource;

    public KeysDataSource(Context context) {
        this.dataSource = KeysDataBase.getInstance(context).getDao();
    }

    //create
    public long insert(NotificationKeysModel model) {
        return dataSource.insert(model);
    }

    //read
    public List<Integer> getKeys(int branch) {
        return dataSource.getKeys(branch);
    }

    //update
    public int update(NotificationKeysModel model) {
        return dataSource.update(model);
    }

    //delete
    public int delete(NotificationKeysModel model) {
        return dataSource.delete(model);
    }

    public NotificationKeysModel getLastInserted() {
        return dataSource.getLastInserted();
    }

    public List<NotificationKeysModel> getBranch(int id) {
        return dataSource.getBranch(id);
    }
}
