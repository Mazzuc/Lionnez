package com.example.cad_login_fb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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
        String habitat = intent.getStringExtra("habitat");
        String pais = intent.getStringExtra("pais");
        String alimentacao = intent.getStringExtra("alimentacao");
        String peso = intent.getStringExtra("peso");
        String altura = intent.getStringExtra("altura");
        String curiosidades = intent.getStringExtra("curiosidades");
        String imagem = intent.getStringExtra("imagem");

        // Exibir detalhes na nova Activity
        TextView textViewTitle = findViewById(R.id.textViewTitleDetalhes);
        TextView textViewDescription = findViewById(R.id.textViewDescriptionDetalhes);
        TextView textViewHabitat = findViewById(R.id.textViewHabitatDetalhes);
        TextView textViewPais = findViewById(R.id.textViewPaisDetalhes);
        TextView textViewAlimentacao = findViewById(R.id.textViewAlimentacaoDetalhes);
        TextView textViewPeso = findViewById(R.id.textViewPesoDetalhes);
        TextView textViewAltura = findViewById(R.id.textViewAlturaDetalhes);
        TextView textViewCuriosidades = findViewById(R.id.textViewCuriosidadesDetalhes);
        ImageView imageViewDetalhes = findViewById(R.id.imageViewDetalhes);
        LinearLayout linearLayoutDetalhes = findViewById(R.id.linearLayoutDetalhes);

        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewHabitat.setText(habitat);
        textViewPais.setText(pais);
        textViewAlimentacao.setText(alimentacao);
        textViewPeso.setText(peso);
        textViewAltura.setText(altura);
        textViewCuriosidades.setText(curiosidades);

        Log.d("DetalhesActivity", "URL da Imagem: " + imagem);

        // Carregar imagem usando Glide
        Glide.with(this)
                .load(imagem)
                .into(imageViewDetalhes);

        linearLayoutDetalhes.setBackground(imageViewDetalhes.getDrawable());
    }
}
