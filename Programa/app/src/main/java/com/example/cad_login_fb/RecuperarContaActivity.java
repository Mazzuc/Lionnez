package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cad_login_fb.databinding.ActivityCadastroBinding;
import com.example.cad_login_fb.databinding.ActivityRecuperarContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContaActivity extends AppCompatActivity {

    private ActivityRecuperarContaBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mwu objeto Auth = Fazendo a conexão com o FireBase
        mAuth = FirebaseAuth.getInstance();


        binding.btnRecuperarConta.setOnClickListener(v -> ValidaDados());

    }

    private void ValidaDados(){

        String email = binding.editEmail.getText().toString().trim();

        //Se o email não foi vazio (isEmpty)

        if(!email.isEmpty()){

            binding.progressBar.setVisibility(View.VISIBLE);

            RecuperarContaFireBase(email);

        } else{

            Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
        }


    }

    private void RecuperarContaFireBase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(this, "Já pode verificar seu e-mail.", Toast.LENGTH_SHORT).show();

            }
            // Ocorreu erro, recebe:
            else{

                Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro.", Toast.LENGTH_SHORT).show();

            }
            binding.progressBar.setVisibility(View.GONE);

        });
    }
}