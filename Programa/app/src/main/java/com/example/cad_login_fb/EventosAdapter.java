package com.example.cad_login_fb;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

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

        // Carregar a imagem usando Glide e definir como fundo do LinearLayout
        Glide.with(holder.itemView.getContext())
                .load(eventos.getImagemUrl())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        holder.linearImage.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Este método pode ser deixado vazio
                    }
                });
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
        public LinearLayout linearImage; // Adicione a referência ao LinearLayout

        public EventosViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTxt);
            horarioTextView = itemView.findViewById(R.id.horarioTxt);
            duracaoTextView = itemView.findViewById(R.id.duracaoTxt);
            diaTextView = itemView.findViewById(R.id.diaTxt);
            linearImage = itemView.findViewById(R.id.LinearImage); // Adicione o ID correto do LinearLayout
        }
    }
}
