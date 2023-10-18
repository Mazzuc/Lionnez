package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Criando a Tela Splash: Tempo de espera de 3 segundos
        new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, TelaPerfilActivity.class));
        }, 3000);
    }
}