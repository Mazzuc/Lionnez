package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class CustonToatAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custon_toat_alert);
    }

    public static ViewDataBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        return DataBindingUtil.inflate(inflater, R.layout.activity_custon_toat_alert, parent, attachToParent);
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, int layoutId, ViewGroup parent, boolean attachToParent) {
        return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent);
    }

}