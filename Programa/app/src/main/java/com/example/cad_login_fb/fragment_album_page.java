package com.example.cad_login_fb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class fragment_album_page extends Fragment {

    private static final String ARG_IMAGE_URIS = "image_uris";
    private List<String> imageUris;

    public static fragment_album_page newInstance(List<String> imageUris) {
        fragment_album_page fragment = new fragment_album_page();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_IMAGE_URIS, new ArrayList<>(imageUris));
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_album_page() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUris = getArguments().getStringArrayList(ARG_IMAGE_URIS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_page, container, false);
        ImageView imageView = view.findViewById(R.id.imageViewAlbum);

        // Exiba a primeira imagem na lista (índice 0)
        if (!imageUris.isEmpty()) {
            String imagePath = imageUris.get(0);
            Glide.with(requireContext()).load(imagePath).into(imageView);
        } else {
            // Lógica para exibir uma imagem padrão ou mensagem quando não houver imagem.
            imageView.setImageResource(R.drawable.albozoom1);
        }

        return view;
    }
}
