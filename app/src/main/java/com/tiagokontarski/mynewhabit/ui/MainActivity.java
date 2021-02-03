package com.tiagokontarski.mynewhabit.ui;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.activitys.AbstractActivity;
import com.tiagokontarski.mynewhabit.data.DataSource;
import com.tiagokontarski.mynewhabit.data.LocalDataSource;

import butterknife.BindView;

public class MainActivity extends AbstractActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onInject() {
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}