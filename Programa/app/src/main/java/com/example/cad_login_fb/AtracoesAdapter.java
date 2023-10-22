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

public class AtracoesAdapter extends RecyclerView.Adapter<AtracoesAdapter.AtracaoViewHolder> {

    private List<Atracao> atracoesList;

    public AtracoesAdapter(List<Atracao> atracoesList) {
        this.atracoesList = atracoesList;
    }

    @NonNull
    @Override
    public AtracaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_atracao, parent, false);
        return new AtracaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtracaoViewHolder holder, int position) {
        Atracao atracao = atracoesList.get(position);
        holder.nomeTextView.setText(atracao.getNome());
        holder.horarioTextView.setText(atracao.getHorario());

        // Carregue a imagem usando Glide (ou outra biblioteca de carregamento de imagens).
        Glide.with(holder.itemView.getContext())
                .load(atracao.getImagemUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return atracoesList.size();
    }

    public class AtracaoViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView horarioTextView;
        public ImageView imageView;

        public AtracaoViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            horarioTextView = itemView.findViewById(R.id.horarioTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
