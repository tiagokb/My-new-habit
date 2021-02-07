package com.tiagokontarski.mynewhabit.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.keys.IntentKeys;
import com.tiagokontarski.mynewhabit.commoms.views.AbstractActivity;
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

    private HabitsViewModel viewModel;
    private String rgSelection;
    private Boolean isEditingMode = false;

    private static final String ITEM_ID = "item_id";

    private int itemId = 0;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HabitsActivity.class);
        context.startActivity(intent);
    }

    public static void launchItem(Context context, int itemId) {
        Intent intent = new Intent(context, HabitsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        viewModel.setDataSource(new RoomDataSource(this));
        isFromClickItem();
    }

    private void observe() {
        viewModel.response.observe(this, it -> {
            if (it) {
                finish();
            }
        });

        viewModel.item.observe(this, model -> {
            if (model == null) {
                Toast.makeText(this, getString(R.string.loading_item_error), Toast.LENGTH_SHORT).show();
                return;
            }

            etDescription.setText(model.getTitle());

            RadioButton button;

            switch (model.getDuration()) {
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
            tpTime.setCurrentHour(Integer.parseInt(model.getHour()));
            tpTime.setCurrentMinute(Integer.parseInt(model.getMinute()));
        });
    }

    @OnTextChanged(R.id.habit_et_description)
    void onTextChanged(CharSequence s) {
        if (etDescription.getText().toString().isEmpty()) {
            btnSave.setEnabled(false);
            tilDescription.setErrorEnabled(true);
            tilDescription.setError(getString(R.string.habit_et_description));
        } else {
            btnSave.setEnabled(true);
            tilDescription.setError("");
            tilDescription.setErrorEnabled(false);
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

        if (isEditingMode) {
            viewModel.update(itemId, description, rgSelection, hour, minute);
        } else {
            viewModel.save(description, rgSelection, hour, minute);
        }


    }

    @Override
    public int getLayout() {
        return R.layout.activity_habits;
    }

}