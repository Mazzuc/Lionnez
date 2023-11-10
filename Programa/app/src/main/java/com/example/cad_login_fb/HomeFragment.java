package com.example.cad_login_fb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout profileLayout;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private List<Atracao> atracoesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AtracoesAdapter adapter;

    private CollectionReference atracoesCollection = firestore.collection("Atracoes");

    private List<Novidades> novidadesList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private NovidadesAdapter adapter2;
    private CollectionReference novidadesCollection = firestore.collection("Novidades");


    private List<Eventos> eventosList = new ArrayList<>();
    private RecyclerView recyclerView3;
    private EventosAdapter adapter3;
    private CollectionReference eventosCollection = firestore.collection("Eventos");

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Configurar o RecyclerView para exibir os itens na horizontal - Card Atrações
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new AtracoesAdapter(atracoesList);
        recyclerView.setAdapter(adapter);

        // Obter dados do Firestore - Card Atrações
        atracoesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    String nome = document.getString("nome");
                    String horario = document.getString("horario");
                    String imagemUrl = document.getString("imagem");
                    Atracao atracao = new Atracao(nome, horario, imagemUrl);
                    atracoesList.add(atracao);
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Configurar o RecyclerView para exibir os itens na horizontal - Card Novidades
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter2 = new NovidadesAdapter(novidadesList);
        recyclerView2.setAdapter(adapter2);

        // Obter dados do Firestore - Card Novidades
        novidadesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    String nome = document.getString("nome");
                    String info = document.getString("info");
                    String imagemUrl = document.getString("image");
                    Novidades novidade = new Novidades(nome, info, imagemUrl);
                    novidadesList.add(novidade);
                }
                adapter2.notifyDataSetChanged();
            }
        });

        // Configurar o RecyclerView para exibir os itens na horizontal - Card Eventos
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter3 = new EventosAdapter(eventosList);
        recyclerView3.setAdapter(adapter3);

        // Obter dados do Firestore - Card Eventos
        eventosCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    String nome = document.getString("nome");
                    String horario = document.getString("horario");
                    String imagemUrl = document.getString("image");
                    String duracao = document.getString("duracao");
                    String dia = document.getString("dia");
                    Eventos eventos = new Eventos(nome, horario, imagemUrl, duracao, dia);
                    eventosList.add(eventos);
                }
                adapter3.notifyDataSetChanged();
            }
        });

        // Adicionar o código para exibir a página de QRCode ao clicar em LinearQR
        LinearLayout linearQR = view.findViewById(R.id.LinearQR);
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
