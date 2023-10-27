package com.example.cad_login_fb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> {

    private List<Eventos> eventosList;

    public EventosAdapter(List<Eventos> eventosList) {
        this.eventosList = eventosList;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_eventos, parent, false);
        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Eventos eventos = eventosList.get(position);
        holder.nomeTextView.setText(eventos.getNome());
        holder.horarioTextView.setText(eventos.getHorario());
        holder.duracaoTextView.setText(eventos.getDuracao());
        holder.diaTextView.setText(eventos.getDia());

        // Carregue a imagem usando Glide (ou outra biblioteca de carregamento de imagens).
        Glide.with(holder.itemView.getContext())
                .load(eventos.getImagemUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public class EventosViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView horarioTextView;
        public TextView duracaoTextView;

        public TextView diaTextView;
        public ImageView imageView;

        public EventosViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTxt);
            horarioTextView = itemView.findViewById(R.id.horarioTxt);
            duracaoTextView = itemView.findViewById(R.id.duracaoTxt);
            diaTextView = itemView.findViewById(R.id.diaTxt);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
