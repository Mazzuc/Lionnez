package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.cad_login_fb.databinding.ActivityLoginBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToastCorrectBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToatAlertBinding;

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

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                LoginFireBase(email, senha);
            } else {

                ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

                // Personalize o texto da mensagem do Toast
                customToastBindingCorrect.textViewalerta.setText("Informe uma senha");

                Toast customToast = new Toast(this);
                customToast.setDuration(Toast.LENGTH_SHORT);
                customToast.setView(customToastBindingCorrect.getRoot());
                customToast.show();
            }
        } else {

            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe seu E-mail");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        }
    }

    private void LoginFireBase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, HomeActivity.class));
            } else {

             //   binding.progressBar.setVisibility(View.GONE);
              //  Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro.", Toast.LENGTH_SHORT).show();

                // Código para mostrar o Toast personalizado
                ActivityCustonToastBinding customToastBinding = ActivityCustonToastBinding.inflate(getLayoutInflater());

                // Personalize o texto da mensagem do Toast
                customToastBinding.textView.setText("Opa, verifique as informações: ocorreu um erro.");

                Toast customToast = new Toast(this);
                customToast.setDuration(Toast.LENGTH_SHORT);
                customToast.setView(customToastBinding.getRoot());
                customToast.show();
            }
        });
    }
}
