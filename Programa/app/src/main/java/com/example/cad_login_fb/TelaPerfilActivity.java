package com.example.cad_login_fb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.Switch;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaPerfilActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private Button mBtnSelectPhoto;
    private Button mBtnUpload;
    private ImageView mImgPhoto;
    private TextView emailTextView;
    private TextView nomeTextView;
    private FirebaseAuth mAuth;
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        mBtnSelectPhoto = findViewById(R.id.btn_selectphoto);
        mBtnUpload = findViewById(R.id.btn_upload);
        mImgPhoto = findViewById(R.id.img_photo);

        emailTextView = findViewById(R.id.emailTextView);
        nomeTextView = findViewById(R.id.nomeTextView); // Adicione esta linha

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail(); // Obtém o email do usuário autenticado
            if (userEmail != null) {
                emailTextView.setText("" + userEmail);
            }
            String userNome = user.getDisplayName(); // Obtém o nome do usuário autenticado
            if (userNome != null) {
                nomeTextView.setText("" + userNome); // Define o nome do usuário no TextView
            }
        }



        switchDarkMode = findViewById(R.id.switch_dark_mode);

        // Defina o estado inicial do Switch com base no tema atual
        switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Altere o tema do aplicativo com base no estado do Switch
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Reconstrua a atividade para aplicar o novo tema
        });


        mBtnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToFirebaseStorage();
            }
        });

        downloadAndDisplayImages();

        // Adicionar o código para exibir o AlertDialog personalizado ao clicar em TxtPolitica
        LinearLayout linearLayout = findViewById(R.id.altsair);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                // Aplicar a forma redonda com Glide
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.transform(new CircleCrop());

                Glide.with(this)
                        .load(bitmap)
                        .apply(requestOptions)
                        .into(mImgPhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (mImgPhoto.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) mImgPhoto.getDrawable()).getBitmap();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + System.currentTimeMillis() + ".jpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TelaPerfilActivity.this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                    downloadAndDisplayImages();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("FirebaseStorage", "Erro ao fazer upload da imagem: " + e.getMessage(), e);
                    Toast.makeText(TelaPerfilActivity.this, "Erro ao carregar a imagem.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(TelaPerfilActivity.this, "Selecione uma imagem primeiro.", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadAndDisplayImages() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Use a biblioteca Glide para carregar e exibir as imagens a partir de "uri"
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.transform(new CircleCrop());

                            Glide.with(TelaPerfilActivity.this)
                                    .load(uri)
                                    .apply(requestOptions)
                                    .into(mImgPhoto);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseStorage", "Erro ao listar itens: " + e.getMessage(), e);
            }
        });
    }

    private void showCustomDialog() {
        // Inflar o layout do AlertDialog personalizado
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custon_sair, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustomStyle);
        builder.setView(dialogView);

        // Criar o AlertDialog
        final AlertDialog customDialog = builder.create();

        // Configurar o clique fora do AlertDialog para fechá-lo
        customDialog.setCanceledOnTouchOutside(true);

        // Exibir o AlertDialog
        customDialog.show();
    }
}

