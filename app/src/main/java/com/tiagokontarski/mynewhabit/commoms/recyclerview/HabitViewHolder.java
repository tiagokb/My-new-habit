package com.tiagokontarski.mynewhabit.commoms.recyclerview;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.listeners.HabitsListeners;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HabitViewHolder extends RecyclerView.ViewHolder {

    private final ConstraintLayout container;
    private final TextView title;
    private final TextView duration;
    private final TextView hourOfDay;

    public HabitViewHolder(@NonNull View itemView) {
        super(itemView);
        container = itemView.findViewById(R.id.item_container);
        title = itemView.findViewById(R.id.rv_item_habit_title);
        duration = itemView.findViewById(R.id.rv_item_habit_duration);
        hourOfDay = itemView.findViewById(R.id.rv_item_habit_clock);

    }

    public void bind(HabitModel model, HabitsListeners listener) {
        String time = model.getHour() + "-" + model.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(model.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(model.getMinute()));

        String timeFormated = new SimpleDateFormat("HH:mm", new Locale("pt-br")).format(calendar.getTime());

        title.setText(model.getTitle());
        duration.setText(model.getDuration());
        hourOfDay.setText(timeFormated);

        container.setOnClickListener(l -> listener.onClickItem(model.getUid()));

        container.setLongClickable(true);
        container.setOnLongClickListener(l -> {
            listener.onLongClickItem(model);
            return true;
        });
    }
}
