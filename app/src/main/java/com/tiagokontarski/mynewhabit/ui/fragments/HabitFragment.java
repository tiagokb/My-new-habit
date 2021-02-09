package com.tiagokontarski.mynewhabit.ui.fragments;

import android.app.AlertDialog;
import android.app.Notification;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.broadcast.NotificationManager;
import com.tiagokontarski.mynewhabit.commoms.listeners.HabitsListeners;
import com.tiagokontarski.mynewhabit.commoms.recyclerview.HabitAdapter;
import com.tiagokontarski.mynewhabit.commoms.views.AbstractFragment;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.KeysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.viewmodel.fragments.FragmentViewModel;

public class HabitFragment extends AbstractFragment<FragmentViewModel> implements HabitsListeners {

    private final HabitAdapter adapter = new HabitAdapter();

    private HabitFragment() {
    }

    public static HabitFragment getFragment(FragmentViewModel viewModel, RoomDataSource dataSource, DaysDataSource daysDataSource, KeysDataSource keysDataSource, NotificationManager manager) {
        HabitFragment fragment = new HabitFragment();
        fragment.setViewModel(viewModel);
        viewModel.setDataSource(dataSource, daysDataSource, keysDataSource, manager);
        return fragment;
    }

    private void confirmationDialog(HabitModel model) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.delete_item, model.getTitle()))
                .setMessage(getString(R.string.delete_message))
                .setPositiveButton(getString(R.string.delete_item_button), (a, b) -> {
                    viewModel.deleteItem(model);
                    adapter.updateList(viewModel.updateList());
                })
                .setNegativeButton(getString(R.string.cancel_delete_item), (a, b) -> {

                })
                .setCancelable(true)
                .create();
        dialog.show();
    }

    @Override
    protected void onInject(View view) {
        RecyclerView rv = view.findViewById(R.id.habits_rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateList(viewModel.updateList());
    }

    @Override
    public void onClickItem(int itemId) {
        viewModel.getItem(itemId);
    }

    @Override
    public void onLongClickItem(HabitModel model) {
        confirmationDialog(model);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_habits;
    }
}
