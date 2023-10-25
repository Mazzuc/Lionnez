package com.example.cad_login_fb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.textcadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textrecuperarconta.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperarContaActivity.class));
        });

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());
    }

    private void ValidaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();

        if (!email.isEmpty() && !senha.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            LoginFireBase(email, senha);
        } else {
            showToast("Informe um email e senha válidos.");
        }
    }

    private void LoginFireBase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Atualize o SharedPreferences para indicar que o usuário está logado
                setLoginStatus(true);
                finish();
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                binding.progressBar.setVisibility(View.GONE);
                showToast("Opa, verifique as informações: ocorreu um erro.");
            }
        });
    }

    private void showToast(String message) {
        ActivityCustonToastBinding customToastBinding = ActivityCustonToastBinding.inflate(getLayoutInflater());
        customToastBinding.textView.setText(message);

        Toast customToast = new Toast(this);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastBinding.getRoot());
        customToast.show();
    }

    // Método para atualizar o estado de login no SharedPreferences
    private void setLoginStatus(boolean isLogged) {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }
}
