package com.example.cad_login_fb;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;

    private ViewPager viewPager;
    private AlbumPagerAdapter albumPagerAdapter;
    private List<fragment_album_page> albumPages;

    private Button btnAnterior;
    private Button btnProximo;
    private Button btnAdicionarImagem;
    private Button btnAbrirCamera;
    private Button btnAbrirGaleria;
    private TextView txtPaginaAtual;

    private ImageView imageView;
    private Button btnDelete;
    private List<String> imagePaths;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager);
        albumPages = new ArrayList<>();

        Button btnToggle = view.findViewById(R.id.btnToggle);
        final LinearLayout boxLayout = view.findViewById(R.id.boxLayout);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxLayout.setVisibility(boxLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        adicionarPaginasDoAlbum();

        albumPagerAdapter = new AlbumPagerAdapter(getChildFragmentManager(), albumPages);
        viewPager.setAdapter(albumPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        btnAnterior = view.findViewById(R.id.btnAnterior);
        btnProximo = view.findViewById(R.id.btnProximo);
        btnAdicionarImagem = view.findViewById(R.id.btnAdicionarImagem);
        btnAbrirCamera = view.findViewById(R.id.btnAbrirCamera);
        btnAbrirGaleria = view.findViewById(R.id.btnAbrirGaleria);
        txtPaginaAtual = view.findViewById(R.id.txtPaginaAtual);

        btnAdicionarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });

        btnAbrirCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });

        btnAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegarAnterior(view);
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegarProximo(view);
            }
        });

        atualizarIndicadorPagina();

        // Código referente à tentativa de deletar
        imageView = view.findViewById(R.id.imageView);
        btnDelete = view.findViewById(R.id.btnDelete);

        // Tentativa de localizar a imagem
        Bundle args = getArguments();
        if (args != null) {
            imagePaths = args.getStringArrayList("imagePaths");
        }

        // Exiba a primeira imagem (se houver)
        if (imagePaths != null && !imagePaths.isEmpty()) {
            exibirImagem(imagePaths.get(0));
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirConfirmacaoExclusao();
            }
        });
    }

    private void exibirImagem(String imagePath) {
        // Utilize o Glide para carregar a imagem no ImageView
        Glide.with(requireContext()).load(imagePath).into(imageView);
    }

    private void exibirConfirmacaoExclusao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Tem certeza que deseja deletar a imagem?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletarImagem();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não fazer nada, o usuário optou por não excluir
                    }
                })
                .show();
    }

    private void deletarImagem() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem >= 0 && currentItem < albumPages.size()) {
            albumPages.remove(currentItem);
            albumPagerAdapter.setPages(albumPages);
            viewPager.setAdapter(albumPagerAdapter);
            atualizarIndicadorPagina();

            Toast.makeText(requireContext(), "Imagem excluída com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            adicionarImagem();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        } else {
            abrirGaleria();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    adicionarImagem();
                } else {
                    // Permissão da câmera negada
                    // Lidar com este caso, mostrar uma mensagem ou desativar a funcionalidade da câmera
                }
                break;

            case REQUEST_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria();
                } else {
                    // Permissão de armazenamento negada
                    // Lidar com este caso, mostrar uma mensagem ou desativar a funcionalidade da galeria
                }
                break;
        }
    }

    private List<String> obterListaDeImagens() {
        return new ArrayList<>();
    }

    private void adicionarPaginasDoAlbum() {
        albumPages.add(fragment_album_page.newInstance(obterListaDeImagens()));
    }

    private void adicionarNovaImagem(fragment_album_page page) {
        albumPages.add(page);
        albumPagerAdapter.setPages(albumPages);
        albumPagerAdapter.notifyDataSetChanged();
        atualizarIndicadorPagina();
    }

    public void adicionarImagem() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            salvarFotoNoAlbum(photo);
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                String imagePath = getImagePath(selectedImageUri);
                if (imagePath != null) {
                    List<String> novaLista = new ArrayList<>();
                    novaLista.add(imagePath);
                    adicionarNovaImagem(fragment_album_page.newInstance(novaLista));
                }
            }
        }
    }

    private void salvarFotoNoAlbum(Bitmap photo) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String imageName = "nome_da_foto_" + timestamp;
        String imagePath = salvarImagemNoDispositivo(photo, imageName);
        List<String> novaLista = new ArrayList<>();
        novaLista.add(imagePath);
        adicionarNovaImagem(fragment_album_page.newInstance(novaLista));
    }

    public void abrirCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void navegarAnterior(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
            atualizarIndicadorPagina();
        }
    }

    public void navegarProximo(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < albumPages.size() - 1) {
            viewPager.setCurrentItem(currentItem + 1);
            atualizarIndicadorPagina();
        }
    }

    private void atualizarIndicadorPagina() {
        int paginaAtual = viewPager.getCurrentItem() + 1;
        int totalPaginas = albumPages.size();
        String textoIndicador = getString(R.string.indicador_pagina, paginaAtual, totalPaginas);
        txtPaginaAtual.setText(textoIndicador);
    }

    private String salvarImagemNoDispositivo(Bitmap image, String imageName) {
        String imagePath = requireContext().getFilesDir() + "/" + imageName + ".png";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return null;
    }

    private static class AlbumPagerAdapter extends FragmentPagerAdapter {

        private List<fragment_album_page> pages;

        public AlbumPagerAdapter(FragmentManager fm, List<fragment_album_page> pages) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.pages = pages;
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        public void setPages(List<fragment_album_page> pages) {
            this.pages = pages;
        }
    }
}
