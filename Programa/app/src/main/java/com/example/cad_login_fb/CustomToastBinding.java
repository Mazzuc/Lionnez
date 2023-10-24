package com.example.cad_login_fb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class CustomToastBinding {
    public static ViewDataBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        return DataBindingUtil.inflate(inflater, R.layout.activity_custon_toast, parent, attachToParent);
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, int layoutId, ViewGroup parent, boolean attachToParent) {
        return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent);
    }
}
