package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EscolhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        View btncadastro;
        View buttonlogin;

        buttonlogin = findViewById(R.id.buttonlogin);
        btncadastro = findViewById(R.id.buttoncriarconta);

        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EscolhaActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EscolhaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Adicionar o código para exibir o AlertDialog personalizado ao clicar em TxtPolitica
        TextView textView = findViewById(R.id.TxtPolitica);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void showCustomDialog() {
        // Inflar o layout do AlertDialog personalizado
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custon_privacidade_politica, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustomStyle);
        builder.setView(dialogView);

        // Criar o AlertDialog
        final AlertDialog customDialog = builder.create();

        // Configurar o clique fora do AlertDialog para fechá-lo
        customDialog.setCanceledOnTouchOutside(true);

        // Exibir o AlertDialog
        customDialog.show();
    }
}
