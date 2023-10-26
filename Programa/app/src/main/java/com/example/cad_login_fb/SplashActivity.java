package com.example.cad_login_fb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Verifique o estado de login no SharedPreferences
        boolean isLogged = getLoginStatus();

        // Tempo de espera de 3 segundos
        new Handler(getMainLooper()).postDelayed(() -> {
            if (!isLogged) {
                // Se o usuário não estiver logado, vá para a tela de escolha
                startActivity(new Intent(this, EscolhaActivity.class));
            } else {
                // Se o usuário estiver logado, vá para a tela Home
                startActivity(new Intent(this, HomeActivity.class));
            }
            finish();
        }, 3000);
    }

    // Método para obter o estado de login do SharedPreferences
    private boolean getLoginStatus() {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("isLogged", false);
    }
}
