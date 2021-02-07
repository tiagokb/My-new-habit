package com.tiagokontarski.mynewhabit.commoms.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tiagokontarski.mynewhabit.R;

public abstract class AbstractFragment<VM extends ViewModel> extends Fragment {

    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        onInject(view);
        return view;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected abstract void onInject(View view);

    @LayoutRes
    protected abstract int getLayout();
}
