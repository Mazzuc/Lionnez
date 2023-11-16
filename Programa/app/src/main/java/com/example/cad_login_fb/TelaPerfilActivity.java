package com.example.cad_login_fb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class TelaPerfilActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private Button mBtnSelectPhoto;
    private Button mBtnUpload;
    private ImageView mImgPhoto;
    private TextView emailTextView;
    private TextView nomeTextView;
    private FirebaseAuth mAuth;
    private Switch switchDarkMode;
    private LinearLayout linearLayoutSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        mBtnSelectPhoto = findViewById(R.id.btn_selectphoto);
        mBtnUpload = findViewById(R.id.btn_upload);
        mImgPhoto = findViewById(R.id.img_photo);
        emailTextView = findViewById(R.id.emailTextView);
        nomeTextView = findViewById(R.id.nomeTextView);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            if (userEmail != null) {
                emailTextView.setText("" + userEmail);
            }
            String userNome = user.getDisplayName();
            if (userNome != null) {
                nomeTextView.setText("" + userNome);
            }
        }

        switchDarkMode = findViewById(R.id.switch_dark_mode);
        switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate();
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

        downloadAndDisplayUserImage();

        linearLayoutSair = findViewById(R.id.altsair);
        linearLayoutSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        //Configurando "LinearVoltar"
        LinearLayout linearVoltar = findViewById(R.id.LinearVoltar);
        linearVoltar.setOnClickListener(view -> onVoltarLayoutClick());
    }

    public void onVoltarLayoutClick() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
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
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                Bitmap bitmap = ((BitmapDrawable) mImgPhoto.getDrawable()).getBitmap();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + userId + ".jpg");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storageReference.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(TelaPerfilActivity.this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();

                        downloadAndDisplayUserImage();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Erro ao fazer upload da imagem: " + e.getMessage(), e);
                        Toast.makeText(TelaPerfilActivity.this, "Erro ao carregar a imagem.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(TelaPerfilActivity.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(TelaPerfilActivity.this, "Selecione uma imagem primeiro.", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadAndDisplayUserImage() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + userId + ".jpg");

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.transform(new CircleCrop());

                    Glide.with(TelaPerfilActivity.this)
                            .load(uri)
                            .apply(requestOptions)
                            .into(mImgPhoto);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("FirebaseStorage", "Erro ao obter imagem do usuário: " + e.getMessage(), e);
                }
            });
        } else {
            Toast.makeText(TelaPerfilActivity.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCustomDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custon_sair, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustomStyle);
        builder.setView(dialogView);

        final AlertDialog customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.show();

        LinearLayout linearSair = dialogView.findViewById(R.id.LinearSair);

        linearSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deslogar o usuário (caso esteja usando Firebase Auth)
                FirebaseAuth.getInstance().signOut();

                // Atualizar o SharedPreferences para indicar que o usuário não está mais conectado
                setLoginStatus(false);

                // Fecha o diálogo
                customDialog.dismiss();

                // Redirecionar o usuário para a tela de escolha
                Intent intent = new Intent(TelaPerfilActivity.this, EscolhaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Encerra a atividade atual
            }
        });
    }

    // Método para atualizar o estado de login no SharedPreferences
    private void setLoginStatus(boolean isLogged) {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }
}
