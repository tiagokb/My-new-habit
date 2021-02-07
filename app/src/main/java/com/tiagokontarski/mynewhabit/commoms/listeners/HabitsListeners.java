package com.tiagokontarski.mynewhabit.commoms.listeners;

import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

public interface HabitsListeners {
    void onClickItem(int itemId);

    void onLongClickItem(HabitModel model);
}
