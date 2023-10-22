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

public class NovidadesAdapter extends RecyclerView.Adapter<NovidadesAdapter.NovidadesViewHolder> {

    private List<Novidades> novidadesList;

    public NovidadesAdapter(List<Novidades> novidadesList) {
        this.novidadesList = novidadesList;
    }

    @NonNull
    @Override
    public NovidadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_novidades, parent, false);
        return new NovidadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovidadesViewHolder holder, int position) {
        Novidades novidades = novidadesList.get(position); // Correção aqui
        holder.nomeTextView.setText(novidades.getNome());
        holder.infoTextView.setText(novidades.getInfo());

        // Carregue a imagem usando Glide (ou outra biblioteca de carregamento de imagens).
        Glide.with(holder.itemView.getContext())
                .load(novidades.getImagemUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return novidadesList.size();
    }

    public class NovidadesViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView infoTextView;
        public ImageView imageView;

        public NovidadesViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            infoTextView = itemView.findViewById(R.id.infoTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
