package com.example.cad_login_fb;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class nav_heaarder extends Fragment {

    private ImageView imageViewRetornar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_header, container, false);
        imageViewRetornar = view.findViewById(R.id.idretornar);
        return view;
    }

    // Método para exibir a imagem na ImageView
    public void exibirImagem(Uri imageUri) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CircleCrop());

        Glide.with(this)
                .load(imageUri)
                .apply(requestOptions)
                .into(imageViewRetornar);
    }
}
