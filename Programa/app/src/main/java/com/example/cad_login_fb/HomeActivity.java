package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout novidadesLayout;
    private LinearLayout atracoesLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        novidadesLayout = findViewById(R.id.novidades_layout);

        addNovidadesCard("Primata Marrom", "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.\n" +
                "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.", R.drawable.novidade1);
        addNovidadesCard("Primata Marrom", "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.\n" +
                "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.", R.drawable.novidade1);

        atracoesLayout = findViewById(R.id.atracoes_layout);

        addAtracoesCard("Colônia das Formigas", "09:00 - 12:00", R.drawable.atracoes1);
        addAtracoesCard("Pavão do Egito", "09:00 - 12:00", R.drawable.atracoes2);
    }

    private void addNovidadesCard(String title, String info, int imageResId) {
        NovidadesCard novidadesCard = new NovidadesCard(this, title, info, imageResId);
        novidadesLayout.addView(novidadesCard);
    }

    private void addAtracoesCard(String title, String info, int imageResId) {
        AtracoesCard atracoesCard = new AtracoesCard(this, title, info, imageResId);
        atracoesLayout.addView(atracoesCard);
    }
}