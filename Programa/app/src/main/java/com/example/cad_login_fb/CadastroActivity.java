package com.example.cad_login_fb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToastCorrectBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToatAlertBinding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToastCorrectBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToatAlertBinding;
import com.example.cad_login_fb.databinding.ActivityCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Meu objeto Auth = Fazendo a conexão com o Firebase
        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());

        //Configurando "LinearVoltar"
        LinearLayout linearVoltar = findViewById(R.id.LinearVoltar);
        linearVoltar.setOnClickListener(view -> onVoltarLayoutClick());
    }

    public void onVoltarLayoutClick() {
        finish();
    }

    private void ValidaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();
        String nome = binding.editNome.getText().toString().trim();

        if (nome.isEmpty()) {
           // Toast.makeText(this, "Informe seu nome", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe seu nome");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else if (email.isEmpty()) {
         //   Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe seu E-mail");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else if (senha.isEmpty()) {
          //  Toast.makeText(this, "Informe uma senha", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe uma senha");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            CriarContaFireBase(email, senha, nome);
        }
    }

    private void CriarContaFireBase(String email, String senha, String nome) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registro com sucesso
                        FirebaseUser user = mAuth.getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nome)
                                .build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                // Redirecionar para a HomeActivity
                                finish();
                                startActivity(new Intent(this, HomeActivity.class));
                            } else {
                                binding.progressBar.setVisibility(View.GONE);
                               // Toast.makeText(this, "Opa, ocorreu um erro ao atualizar o nome", Toast.LENGTH_SHORT).show();
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
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                      //  Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro", Toast.LENGTH_SHORT).show();

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
