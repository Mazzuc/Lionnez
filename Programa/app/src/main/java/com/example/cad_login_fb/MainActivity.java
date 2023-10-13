package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
    }
}