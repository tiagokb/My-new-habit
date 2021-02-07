package com.tiagokontarski.mynewhabit.commoms.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.listeners.HabitsListeners;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

import java.util.ArrayList;
import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitViewHolder> {

    private List<HabitModel> list = new ArrayList<>();

    private HabitsListeners listener;

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_rv_habits_item, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<HabitModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(HabitsListeners listener) {
        this.listener = listener;
    }
}
