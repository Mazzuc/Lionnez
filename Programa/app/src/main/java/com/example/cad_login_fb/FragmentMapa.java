package com.example.cad_login_fb;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragmentMapa extends Fragment {

    private ImageView imagemMapa;
    private float escalaAtual = 2.7f;
    private static final float ESCALA_MINIMA = 2.0f;
    private static final float ESCALA_MAXIMA = 4.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private float ultimaPosX, ultimaPosY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        imagemMapa = rootView.findViewById(R.id.imagemMapa);

        // Configurar ScaleGestureDetector e GestureDetector apenas para a ImageView
        scaleGestureDetector = new ScaleGestureDetector(requireContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                escalaAtual *= scaleFactor;
                escalaAtual = Math.max(ESCALA_MINIMA, Math.min(escalaAtual, ESCALA_MAXIMA));

                imagemMapa.setScaleX(escalaAtual);
                imagemMapa.setScaleY(escalaAtual);
                return true;
            }
        });

        gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                ultimaPosX = e.getX();
                ultimaPosY = e.getY();
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float deltaX = e2.getX() - ultimaPosX;
                float deltaY = e2.getY() - ultimaPosY;

                imagemMapa.setTranslationX(imagemMapa.getTranslationX() + deltaX);
                imagemMapa.setTranslationY(imagemMapa.getTranslationY() + deltaY);

                escalaAtual = Math.max(ESCALA_MINIMA, escalaAtual);

                ultimaPosX = e2.getX();
                ultimaPosY = e2.getY();
                return true;
            }
        });

        imagemMapa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        // Configuração dos layouts lineares...
        LinearLayout layoutZoomIn = rootView.findViewById(R.id.layoutZoomIn);
        layoutZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomIn();
            }
        });

        LinearLayout layoutZoomOut = rootView.findViewById(R.id.layoutZoomOut);
        layoutZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomOut();
            }
        });

        // Configuração do layout de download
        LinearLayout layoutDownload = rootView.findViewById(R.id.layoutDownload);
        layoutDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    downloadPdf();
                } else {
                    requestStoragePermission();
                }
            }
        });

        // Configurar o zoom inicial
        configurarZoomInicial();

        return rootView;
    }

    private void configurarZoomInicial() {
        imagemMapa.setScaleX(escalaAtual);
        imagemMapa.setScaleY(escalaAtual);
    }

    private void zoomIn() {
        escalaAtual += 0.1f;
        escalaAtual = Math.min(ESCALA_MAXIMA, escalaAtual);
        imagemMapa.setScaleX(escalaAtual);
        imagemMapa.setScaleY(escalaAtual);
    }

    private void zoomOut() {
        escalaAtual -= 0.1f;
        escalaAtual = Math.max(ESCALA_MINIMA, escalaAtual);
        imagemMapa.setScaleX(escalaAtual);
        imagemMapa.setScaleY(escalaAtual);
    }

    private void downloadPdf() {
        String fileName = "MapaZooCuritiba.pdf";
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, fileName);

        try {
            InputStream inputStream = requireContext().getAssets().open(fileName);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

            // Após o download, você pode abrir o PDF
            abrirPdf(file);

            // Informe ao usuário que o download foi concluído
            Toast.makeText(requireContext(), "Download concluído: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Informe ao usuário se ocorrer um erro durante o download
            Toast.makeText(requireContext(), "Erro durante o download do PDF.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirPdf(File file) {
        Uri path = FileProvider.getUriForFile(requireContext(), "com.example.cad_login_fb.provider", file);

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            // Caso não tenha um aplicativo para abrir PDF, avise o usuário
            Toast.makeText(requireContext(), "Nenhum aplicativo disponível para abrir PDF.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
