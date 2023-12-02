package com.example.cad_login_fb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new AtracoesAdapter(atracoesList);
        recyclerView.setAdapter(adapter);

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

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter2 = new NovidadesAdapter(novidadesList);
        recyclerView2.setAdapter(adapter2);

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

        recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter3 = new EventosAdapter(eventosList);
        recyclerView3.setAdapter(adapter3);

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

        LinearLayout linearHorario = view.findViewById(R.id.LinearHorario);
        linearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        LinearLayout LinearQR = view.findViewById(R.id.LinearQR);
        LinearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedbackGeralDialog();
            }
        });

        profileLayout = view.findViewById(R.id.LinearProfile);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), TelaPerfilActivity.class);
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



    private void showFeedbackGeralDialog() {
        RateUsDialogFragment feedbackGeralFragment = new RateUsDialogFragment();
        feedbackGeralFragment.show(getFragmentManager(), "feedback_dialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogCustomStyle);

        final AlertDialog customDialog = builder.create();

        // Configurar o clique fora do AlertDialog para fechá-lo
        customDialog.setCanceledOnTouchOutside(true);

        // Exibir o AlertDialog
        customDialog.show();
    }
}
