package com.example.cad_login_fb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// DetalhesActivity.java
public class DetalhesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // Obter detalhes do item clicado
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");

        // Exibir detalhes na nova Activity
        TextView textViewTitle = findViewById(R.id.textViewTitleDetalhes);
        TextView textViewDescription = findViewById(R.id.textViewDescriptionDetalhes);

        textViewTitle.setText(title);
        textViewDescription.setText(description);
    }
}
