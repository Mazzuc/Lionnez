package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     View btnteste;
        btnteste = findViewById(R.id.btnteste);

        btnteste.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, TelaPerfilActivity.class);
            startActivity(intent);
        });

        View btntesteimg;
        btntesteimg = findViewById(R.id.btnimgteste);

        btntesteimg.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, TelaTesteImg.class);
            startActivity(intent);
        });
        switchDarkMode = findViewById(R.id.switch_dark_mode);

        // Defina o estado inicial do Switch com base no tema atual
        switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Altere o tema do aplicativo com base no estado do Switch
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Reconstrua a atividade para aplicar o novo tema
        });
    }
}
