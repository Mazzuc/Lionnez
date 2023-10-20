package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cad_login_fb.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Jeito mais simples de Intent
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Mwu objeto Auth = Fazendo a conexão com o FireBase
        mAuth = FirebaseAuth.getInstance();

        binding.textcadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textrecuperarconta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());
    }

    private void ValidaDados(){

        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();

        //Se o email não foi vazio (isEmpty)

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                binding.progressBar.setVisibility(View.VISIBLE);

                LoginFireBase(email, senha);
            } else{
                Toast.makeText(this, "Informe uma senha", Toast.LENGTH_SHORT).show();

            }

        } else{

            Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
        }


    }

    private void LoginFireBase(String email, String senha ){
        mAuth.signInWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(task -> {

            // Se o cadastro ocorreu com sucesso
            if(task.isSuccessful()){

                //como deu certo, será levado para home
                finish();
                startActivity(new Intent(this, HomeActivity.class));

            }
            // Ocorreu erro, recebe:
            else{

                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro.", Toast.LENGTH_SHORT).show();

            }
        });
    }

}