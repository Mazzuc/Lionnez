package com.example.cad_login_fb;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    private LinearLayout novidadesLayout;
    private LinearLayout atracoesLayout;
    private LinearLayout profileLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Cards Novidades
        novidadesLayout = view.findViewById(R.id.novidades_layout);

        addNovidadesCard("Primata Marrom", "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.\n" +
                "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.", R.drawable.novidade1);
        addNovidadesCard("Primata Marrom", "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.\n" +
                "Nova espécie de Primata Marrom, chega ao Zoo Curitiba.", R.drawable.novidade1);

        // Cards Atrações
        atracoesLayout = view.findViewById(R.id.atracoes_layout);

        addAtracoesCard("Colônia das Formigas", "09:00 - 12:00", R.drawable.atracoes1);
        addAtracoesCard("Pavão do Egito", "09:00 - 12:00", R.drawable.atracoes2);

        LinearLayout linearQR = view.findViewById(R.id.LinearQR);


        // Adicionar o código para exiber a página de QRCode ao clicar em LinearQR
        linearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar a transição para a tela QrCode
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new QrCode())
                        .addToBackStack(null)
                        .commit();
                                        }
        });

        // Adicionar o código para exibir o AlertDialog personalizado ao clicar em LinearHorario
        LinearLayout linearHorario = view.findViewById(R.id.LinearHorario);

        linearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        // Referenciar o LinearLayout "LinearProfile" e adicionar OnClickListener
        profileLayout = view.findViewById(R.id.LinearProfile);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crie um Intent para iniciar a MainActivity
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void addNovidadesCard(String title, String info, int imageResId) {
        NovidadesCard novidadesCard = new NovidadesCard(requireContext(), title, info, imageResId);
        novidadesLayout.addView(novidadesCard);
    }

    private void addAtracoesCard(String title, String info, int imageResId) {
        AtracoesCard atracoesCard = new AtracoesCard(requireContext(), title, info, imageResId);
        atracoesLayout.addView(atracoesCard);
    }

    private void showCustomDialog() {
        // Inflar o layout do AlertDialog personalizado
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_horario, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogCustomStyle);
        builder.setView(dialogView);

        // Criar o AlertDialog
        final AlertDialog customDialog = builder.create();

        // Configurar o clique fora do AlertDialog para fechá-lo
        customDialog.setCanceledOnTouchOutside(true);

        // Exibir o AlertDialog
        customDialog.show();
    }
}
