package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
public class testealbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testealbum);
        Button btnToggle = findViewById(R.id.btnToggle);
        final LinearLayout boxLayout = findViewById(R.id.boxLayout);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (boxLayout.getVisibility() == View.VISIBLE) {
                    boxLayout.setVisibility(View.GONE);
                } else {
                    boxLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}