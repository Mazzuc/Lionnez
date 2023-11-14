package com.example.cad_login_fb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activity_album extends AppCompatActivity {

    private ViewPager viewPager;
    private AlbumPagerAdapter albumPagerAdapter;
    private List<fragment_album_page> albumPages;

    private Button btnAnterior;
    private Button btnProximo;
    private Button btnAdicionarImagem;
    private Button btnAbrirCamera;
    private Button btnAbrirGaleria;
    private TextView txtPaginaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        viewPager = findViewById(R.id.viewPager);
        albumPages = new ArrayList<>();

        // Adicione as páginas do álbum (pode ser vazio inicialmente)
        albumPages.add(fragment_album_page.newInstance(obterListaDeImagens()));
        albumPages.add(fragment_album_page.newInstance(obterListaDeImagens()));

        albumPagerAdapter = new AlbumPagerAdapter(getSupportFragmentManager(), albumPages);
        viewPager.setAdapter(albumPagerAdapter);

        btnAnterior = findViewById(R.id.btnAnterior);
        btnProximo = findViewById(R.id.btnProximo);
        btnAdicionarImagem = findViewById(R.id.btnAdicionarImagem);
        btnAbrirCamera = findViewById(R.id.btnAbrirCamera);
        btnAbrirGaleria = findViewById(R.id.btnAbrirGaleria);
        txtPaginaAtual = findViewById(R.id.txtPaginaAtual);

        atualizarIndicadorPagina();  // Atualizar o indicador de página inicialmente
    }

    // Método para adicionar uma nova imagem ao álbum
    private void adicionarNovaImagem(fragment_album_page page) {
        albumPages.add(page);
        albumPagerAdapter.notifyDataSetChanged();
        atualizarIndicadorPagina();
    }

    // Implemente a lógica para permitir ao usuário adicionar novas imagens
    // Chame adicionarNovaImagem() quando uma nova imagem for selecionada.
    public void adicionarImagem(View view) {
        // Lógica para adicionar uma nova imagem (substitua isso pela lógica real)
        // Aqui, chamamos a câmera para tirar uma foto como exemplo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    // Sobrescreva onActivityResult para lidar com o resultado da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            salvarFotoNoAlbum(photo);
        }
    }

    // Método para salvar a foto no álbum
    private void salvarFotoNoAlbum(Bitmap photo) {
        // Lógica para salvar a foto no álbum
        // Adicione o caminho da imagem ao seu modelo de dados
        String imagePath = salvarImagemNoDispositivo(photo, "nome_da_foto");
        // Atualize a lista de páginas do álbum
        List<String> novaLista = new ArrayList<>();
        novaLista.add(imagePath);
        adicionarNovaImagem(fragment_album_page.newInstance(novaLista));
    }

    // Método para abrir a câmera
    public void abrirCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    // Método para abrir a galeria
    public void abrirGaleria(View view) {
        // Lógica para abrir a galeria (substitua isso pela lógica real)
    }

    // Método para navegar para a página anterior
    public void navegarAnterior(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
            atualizarIndicadorPagina();
        }
    }

    // Método para navegar para a próxima página
    public void navegarProximo(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < albumPages.size() - 1) {
            viewPager.setCurrentItem(currentItem + 1);
            atualizarIndicadorPagina();
        }
    }

    // Método para atualizar o indicador de página
    private void atualizarIndicadorPagina() {
        int paginaAtual = viewPager.getCurrentItem() + 1;
        int totalPaginas = albumPages.size();
        String textoIndicador = getString(R.string.indicador_pagina, paginaAtual, totalPaginas);
        txtPaginaAtual.setText(textoIndicador);
    }

    // Método para obter uma lista de URIs de imagens (substitua isso pela lógica real)
    private List<String> obterListaDeImagens() {
        // Lógica para obter uma lista de URIs de imagens
        // Por exemplo, você pode ter uma lista estática ou recuperar de um servidor.
        List<String> listaDeImagens = new ArrayList<>();
        listaDeImagens.add("URL_OU_CAMINHO_DA_IMAGEM_1");
        listaDeImagens.add("URL_OU_CAMINHO_DA_IMAGEM_2");
        // Adicione mais URIs conforme necessário
        return listaDeImagens;
    }

    // Método para salvar a imagem no dispositivo
    private String salvarImagemNoDispositivo(Bitmap image, String imageName) {
        // Lógica para salvar a imagem no dispositivo
        // Aqui você pode escolher o local de armazenamento adequado (por exemplo, armazenamento interno ou externo)
        // Retorne o caminho da imagem
        String imagePath = getApplicationContext().getFilesDir() + "/" + imageName + ".png";
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

    // Classe AlbumPagerAdapter
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
    }
}
