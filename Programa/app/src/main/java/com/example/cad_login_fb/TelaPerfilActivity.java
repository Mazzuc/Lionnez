package com.example.cad_login_fb;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaPerfilActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView nomeTextView;
    private FirebaseAuth mAuth;
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        emailTextView = findViewById(R.id.emailTextView);
        nomeTextView = findViewById(R.id.nomeTextView); // Adicione esta linha

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail(); // Obtém o email do usuário autenticado
            if (userEmail != null) {
                emailTextView.setText("" + userEmail);
            }
            String userNome = user.getDisplayName(); // Obtém o nome do usuário autenticado
            if (userNome != null) {
                nomeTextView.setText("" + userNome); // Define o nome do usuário no TextView
            }
        }



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
