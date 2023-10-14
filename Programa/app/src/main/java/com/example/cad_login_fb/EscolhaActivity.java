package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

                Intent intent = new Intent(EscolhaActivity.this,CadastroActivity.class);
                startActivity(intent);
            }
        });

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EscolhaActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}