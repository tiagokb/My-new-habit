package com.tiagokontarski.mynewhabit.ui;


import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.views.AbstractActivity;
import com.tiagokontarski.mynewhabit.data.PersonDataSource;
import com.tiagokontarski.mynewhabit.data.PersonPersonDataBase;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.ui.fragments.HabitFragment;
import com.tiagokontarski.mynewhabit.ui.fragments.HintsFragment;
import com.tiagokontarski.mynewhabit.viewmodel.factory.fragments.FragmentViewModelFactory;
import com.tiagokontarski.mynewhabit.viewmodel.fragments.FragmentViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_tv_person_name)
    TextView personTitle;

    @BindView(R.id.main_fab)
    FloatingActionButton fab;

    private HabitFragment habitFragment;
    private HintsFragment hintsFragment;
    private Fragment actual;

    private PersonDataSource personDataSource = new PersonPersonDataBase(this);

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onInject() {
        personTitle.setText(getString(R.string.main_welcome_person_name, personDataSource.getPersonName()));

        FragmentViewModel fragmentViewModel =
                new ViewModelProvider(this,
                        new FragmentViewModelFactory(getApplication()))
                        .get(FragmentViewModel.class);

        RoomDataSource dataSource = new RoomDataSource(this);

        habitFragment = HabitFragment.getFragment(fragmentViewModel, dataSource);
        hintsFragment = new HintsFragment();
        actual = habitFragment;

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_fragment, habitFragment).hide(habitFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, hintsFragment).hide(hintsFragment).commit();
        manager.beginTransaction().show(actual).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView bnv = findViewById(R.id.main_bnv);
        bnv.setOnNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.main_fab)
    void onFabClick() {
        HabitsActivity.launch(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();

        if (item.getItemId() == R.id.item_main_nav_habits) {
            fm.beginTransaction().hide(actual).show(habitFragment).commit();
            actual = habitFragment;
            return true;
        } else {
            fm.beginTransaction().hide(actual).show(hintsFragment).commit();
            actual = hintsFragment;
            return true;
        }
    }
}