package com.example.cad_login_fb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cad_login_fb.databinding.ActivityCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mwu objeto Auth = Fazendo a conexão com o FireBase
        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());
    }

    private void ValidaDados() {

        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();

        //Se o email não foi vazio (isEmpty)

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                CriarContaFireBase(email, senha);
            } else {
                Toast.makeText(this, "Informe uma senha", Toast.LENGTH_SHORT).show();

            }

        } else {

            Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
        }


    }

    private void CriarContaFireBase(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {

            // Se o cadastro ocorreu com sucesso
            if (task.isSuccessful()) {

                //como deu certo, será levado para home
                finish();
                startActivity(new Intent(this, HomeActivity.class));

            }
            // Ocorreu erro, recebe:
            else {

                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

