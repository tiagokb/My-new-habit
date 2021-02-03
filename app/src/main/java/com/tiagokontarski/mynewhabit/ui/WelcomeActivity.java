package com.tiagokontarski.mynewhabit.ui;

import androidx.lifecycle.ViewModelProvider;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.activitys.AbstractActivity;
import com.tiagokontarski.mynewhabit.data.DataSource;
import com.tiagokontarski.mynewhabit.data.LocalDataSource;
import com.tiagokontarski.mynewhabit.viewmodel.WelcomeViewModel;
import com.tiagokontarski.mynewhabit.viewmodel.factory.WelcomeViewModelFactory;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class WelcomeActivity extends AbstractActivity {

    @BindView(R.id.welcome_til_person_name)
    TextInputLayout tilPersonName;

    @BindView(R.id.welcome_et_person_name)
    EditText etPersonName;

    @BindView(R.id.welcome_btn_enter)
    Button btnEnter;

    private WelcomeViewModel viewModel;
    private final DataSource dataSource = new LocalDataSource(this);

    @Override
    public void onInject() {
        viewModel = new ViewModelProvider(this, new WelcomeViewModelFactory(getApplication())).get(WelcomeViewModel.class);
        viewModel.setDataSource(dataSource);

        observe();

        verifyUserCreated();
    }

    private void verifyUserCreated() {
        if (viewModel.verifyUserCreated()) {
            launchMainActivity();
        }
    }

    private void observe() {
        viewModel.dataResponse.observe(this, response -> {
            if (response) {
                launchMainActivity();
            } else {
                Toast.makeText(this, getString(R.string.save_person_name_data_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchMainActivity() {
        MainActivity.launch(this);
    }

    @OnClick(R.id.welcome_btn_enter)
    void onEnterClick() {
        viewModel.savePersonName(etPersonName.getText().toString());
    }

    @OnTextChanged(R.id.welcome_et_person_name)
    void onTextChanged(CharSequence s) {
        if (etPersonName.getText().toString().isEmpty()) {
            btnEnter.setEnabled(false);
        } else {
            btnEnter.setEnabled(true);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }
}