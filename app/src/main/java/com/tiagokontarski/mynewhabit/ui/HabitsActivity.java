package com.tiagokontarski.mynewhabit.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.broadcast.NotificationManager;
import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.commoms.views.AbstractActivity;
import com.tiagokontarski.mynewhabit.data.DaysDataSource;
import com.tiagokontarski.mynewhabit.data.KeysDataSource;
import com.tiagokontarski.mynewhabit.data.RoomDataSource;
import com.tiagokontarski.mynewhabit.viewmodel.HabitsViewModel;
import com.tiagokontarski.mynewhabit.viewmodel.factory.HabitsViewModelFactory;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class HabitsActivity extends AbstractActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.habit_til_description)
    TextInputLayout tilDescription;

    @BindView(R.id.habit_et_description)
    EditText etDescription;

    @BindView(R.id.habit_rg_duration)
    RadioGroup rgDuration;

    @BindView(R.id.habit_tp_time)
    TimePicker tpTime;

    @BindView(R.id.habit_btn_save)
    Button btnSave;

    @BindView(R.id.habit_cb_sunday)
    CheckBox sunday;

    @BindView(R.id.habit_cb_monday)
    CheckBox monday;

    @BindView(R.id.habit_cb_tuesday)
    CheckBox tuesday;

    @BindView(R.id.habit_cb_wednesday)
    CheckBox wednesday;

    @BindView(R.id.habit_cb_thursday)
    CheckBox thursday;

    @BindView(R.id.habit_cb_friday)
    CheckBox friday;

    @BindView(R.id.habit_cb_saturday)
    CheckBox saturday;

    private Boolean etEnabled = false;
    private Boolean cbEnabled = false;

    private HabitsViewModel viewModel;
    private String rgSelection;
    private Boolean isEditingMode = false;

    private static final String ITEM_ID = "item_id";

    private int itemId = 0;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HabitsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void launchItem(Context context, int itemId) {
        Intent intent = new Intent(context, HabitsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ITEM_ID, itemId);
        context.startActivity(intent);
    }


    @Override
    public void onInject() {
        viewModel = new ViewModelProvider(this, new HabitsViewModelFactory(getApplication())).get(HabitsViewModel.class);
        observe();
        tpTime.setIs24HourView(true);
        rgSelection = getString(R.string.extra_low);
        rgDuration.setOnCheckedChangeListener(this);
        rgDuration.check(R.id.habit_rb_extra_low);
    }

    private void isFromClickItem() {
        int id = getIntent().getIntExtra(ITEM_ID, 0);
        if (id > 0) {
            itemExists(id);
        }
    }

    private void itemExists(int id) {
        isEditingMode = true;
        itemId = id;
        viewModel.setActualItem(id);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel.setDataSource(new RoomDataSource(this), new DaysDataSource(this));
        isFromClickItem();
    }

    private void observe() {

        viewModel.finalResponse.observe(this, response -> {
            if (response) {
                MainActivity.launch(this);
            } else {
                Toast.makeText(this, getString(R.string.creating_item_error), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.response.observe(this, response -> {
            if (response) {
                HabitModel model = viewModel.getNewestModel();
                NotificationManager manager = new NotificationManager(this);
                viewModel.setNotification(this, manager, model, viewModel.getDaysOfWeek(model.getUid()), new KeysDataSource(this), isEditingMode);
            }
        });

        viewModel.item.observe(this, model -> {
            if (model == null) {
                Toast.makeText(this, getString(R.string.loading_item_error), Toast.LENGTH_SHORT).show();
                return;
            }
            DaysModel daysModel = viewModel.getDaysOfWeek(model.getUid());

            populateItens(model, daysModel);
        });
    }

    private void populateItens(HabitModel habitModel, DaysModel daysModel) {
        etDescription.setText(habitModel.getTitle());
        RadioButton button;
        switch (habitModel.getDuration()) {
            case "15 min":
                button = findViewById(R.id.habit_rb_extra_low);
                break;
            case "30 min":
                button = findViewById(R.id.habit_rb_low);
                break;
            case "45 min":
                button = findViewById(R.id.habit_rb_regular);
                break;
            case "60 min":
                button = findViewById(R.id.habit_rb_high);
                break;
            default:
                button = findViewById(R.id.habit_rb_extra_low);
                break;
        }

        rgDuration.check(button.getId());
        rgSelection = button.getText().toString();
        tpTime.setCurrentHour(Integer.parseInt(habitModel.getHour()));
        tpTime.setCurrentMinute(Integer.parseInt(habitModel.getMinute()));

        sunday.setChecked(daysModel.getSunday());
        monday.setChecked(daysModel.getMonday());
        tuesday.setChecked(daysModel.getTuesday());
        wednesday.setChecked(daysModel.getWednesday());
        thursday.setChecked(daysModel.getThursday());
        friday.setChecked(daysModel.getFriday());
        saturday.setChecked(daysModel.getSaturnday());
    }

    public void onCheckBoxClicked(View view) {
        if (
                sunday.isChecked() ||
                        monday.isChecked() ||
                        tuesday.isChecked() ||
                        wednesday.isChecked() ||
                        thursday.isChecked() ||
                        friday.isChecked() ||
                        saturday.isChecked()) {
            cbEnabled = true;
            setButtonEnabled(etEnabled, cbEnabled);
        } else {
            cbEnabled = false;
            setButtonEnabled(etEnabled, cbEnabled);
        }
    }

    void setButtonEnabled(Boolean et, Boolean cb) {
        if (et && cb) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    @OnTextChanged(R.id.habit_et_description)
    void onTextChanged(CharSequence s) {
        if (etDescription.getText().toString().isEmpty()) {
            tilDescription.setErrorEnabled(true);
            tilDescription.setError(getString(R.string.habit_et_description));
            etEnabled = false;
            setButtonEnabled(etEnabled, cbEnabled);
        } else {
            tilDescription.setError("");
            tilDescription.setErrorEnabled(false);
            etEnabled = true;
            setButtonEnabled(etEnabled, cbEnabled);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton button = findViewById(checkedId);
        rgSelection = button.getText().toString();
    }

    @OnClick(R.id.habit_btn_save)
    void onSaveClick() {
        String description = etDescription.getText().toString();
        String hour = String.valueOf(tpTime.getCurrentHour());
        String minute = String.valueOf(tpTime.getCurrentMinute());

        DaysModel daysOfWeek = new DaysModel(
                sunday.isChecked(),
                monday.isChecked(),
                tuesday.isChecked(),
                wednesday.isChecked(),
                thursday.isChecked(),
                friday.isChecked(),
                saturday.isChecked());

        if (isEditingMode) {
            daysOfWeek.setId(itemId);
            viewModel.update(itemId, description, rgSelection, hour, minute, daysOfWeek);
        } else {
            viewModel.save(description, rgSelection, hour, minute, daysOfWeek);
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.launch(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_habits;
    }

}