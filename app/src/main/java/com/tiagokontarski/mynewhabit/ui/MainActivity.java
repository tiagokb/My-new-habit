package com.tiagokontarski.mynewhabit.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.activitys.AbstractActivity;
import com.tiagokontarski.mynewhabit.data.DataSource;
import com.tiagokontarski.mynewhabit.data.LocalDataSource;
import com.tiagokontarski.mynewhabit.ui.fragments.HabitFragment;

import butterknife.BindView;

public class MainActivity extends AbstractActivity {

    @BindView(R.id.main_tv_person_name)
    TextView personTitle;

    private DataSource dataSource = new LocalDataSource(this);

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onInject() {

        personTitle.setText(getString(R.string.main_welcome_person_name, dataSource.getPersonName()));

        HabitFragment frag = new HabitFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_fragment, frag, "habit_fragment").commit();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}