package com.tiagokontarski.mynewhabit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;

import java.util.ArrayList;
import java.util.List;

public class HabitFragment extends Fragment {

    private RecyclerView rv;
    private HabitAdapter adapter = new HabitAdapter();

    private List<HabitModel> list = new ArrayList<>();

    public HabitFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_habits, container, false);

        rv = view.findViewById(R.id.habits_rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        adapter.setList(mockUpList());

        return view;
    }

    private List<HabitModel> mockUpList() {
        for (int i = 0; i <= 15; i++) {
            HabitModel model = new HabitModel();
            model.setTitle("Leitura");
            model.setDuration("15 min por dia");
            model.setHourOfDay("20:00");

            list.add(model);
        }

        return list;
    }

    private class HabitAdapter extends RecyclerView.Adapter<HabitViewHolder> {

        List<HabitModel> list = new ArrayList<>();

        @NonNull
        @Override
        public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.main_rv_habits_item, parent, false);
            return new HabitViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
            holder.bind(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setList(List<HabitModel> list) {
            this.list = list;
        }
    }

    private static class HabitViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView duration;
        private final TextView hourOfDay;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rv_item_habit_title);
            duration = itemView.findViewById(R.id.rv_item_habit_duration);
            hourOfDay = itemView.findViewById(R.id.rv_item_habit_clock);

        }

        public void bind(HabitModel model) {
            title.setText(model.getTitle());
            duration.setText(model.getDuration());
            hourOfDay.setText(model.getHourOfDay());
        }
    }
}
