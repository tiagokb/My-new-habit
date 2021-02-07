package com.tiagokontarski.mynewhabit.ui.fragments;

import android.view.View;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.views.AbstractFragment;
import com.tiagokontarski.mynewhabit.viewmodel.fragments.FragmentViewModel;

public class HintsFragment extends AbstractFragment<FragmentViewModel> {

    @Override
    protected void onInject(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_hints;
    }
}
