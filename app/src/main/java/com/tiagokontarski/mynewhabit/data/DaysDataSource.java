package com.tiagokontarski.mynewhabit.data;

import android.content.Context;

import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.data.room.days.DaysDao;
import com.tiagokontarski.mynewhabit.data.room.days.DaysDataBase;

public class DaysDataSource {

    private DaysDao dataBase;

    public DaysDataSource(Context context) {
        if (dataBase == null) {
            dataBase = DaysDataBase.getInstance(context).getDao();
        }
    }

    //Create
    public long insert(DaysModel model) {
        return dataBase.insert(model);
    }

    //Read
    public DaysModel get(int id) {
        return dataBase.getSelected(id);
    }

    //Update
    public int update(DaysModel model) {
        return dataBase.update(model);
    }

    //Delete
    public int delete(DaysModel model) {
        return dataBase.delete(model);
    }

}
