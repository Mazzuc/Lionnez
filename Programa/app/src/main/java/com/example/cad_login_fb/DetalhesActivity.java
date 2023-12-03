package com.example.cad_login_fb;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class DetalhesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // Obter detalhes do item clicado
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String habitat = intent.getStringExtra("habitatResum");
        String pais = intent.getStringExtra("pais");
        String alimentacao = intent.getStringExtra("alimentacao");
        String peso = intent.getStringExtra("peso");
        String altura = intent.getStringExtra("altura");
        String curiosidades = intent.getStringExtra("curiosidades");
        String imagem = intent.getStringExtra("imagem");

        // Exibir detalhes na nova Activity
        TextView textViewTitle = findViewById(R.id.textViewTitleDetalhes);
        TextView textViewTitle2 = findViewById(R.id.txtNomeAni);
        TextView textViewDescription = findViewById(R.id.textViewDescriptionDetalhes);
        TextView textViewHabitat = findViewById(R.id.textViewHabitatDetalhes);
        TextView textViewPais = findViewById(R.id.textViewPaisDetalhes);
        TextView textViewAlimentacao = findViewById(R.id.textViewAlimentacaoDetalhes);
        TextView textViewPeso = findViewById(R.id.textViewPesoDetalhes);
        TextView textViewAltura = findViewById(R.id.textViewAlturaDetalhes);
        TextView textViewCuriosidades = findViewById(R.id.textViewCuriosidadesDetalhes);
        ImageView imageViewDetalhes = findViewById(R.id.imageViewDetalhes);
        LinearLayout linearLayoutDetalhes = findViewById(R.id.linearLayoutDetalhamento);

        textViewTitle.setText(title);
        textViewTitle2.setText(title);
        textViewDescription.setText(description);
        textViewHabitat.setText(habitat);
        textViewPais.setText(pais);
        textViewAlimentacao.setText(alimentacao);
        textViewPeso.setText(peso);
        textViewAltura.setText(altura);
        textViewCuriosidades.setText(curiosidades);

        Log.d("DetalhesActivity", "URL da Imagem: " + imagem);

        // Carregar imagem usando Glide with the same logic as in SearchResultAdapter
        Glide.with(this)
                .load(imagem)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300, 300)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayoutDetalhes.setBackgroundResource(0);
                        linearLayoutDetalhes.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
}
