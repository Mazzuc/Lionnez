package com.example.cad_login_fb;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TelaTesteImg extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private Button mBtnSelectPhoto;
    private Button mBtnUpload;
    private ImageView mImgPhoto;
    private Uri mSelectedImageUri;
    private String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testedeco);

        mBtnSelectPhoto = findViewById(R.id.btn_selectphoto);
        mBtnUpload = findViewById(R.id.btn_upload);
        mImgPhoto = findViewById(R.id.img_photo);

        mBtnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abra a galeria para selecionar uma imagem
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifique se uma imagem foi selecionada
                if (mSelectedImageUri != null) {
                    uploadImageToFirebaseStorage();
                } else {
                    Toast.makeText(TelaTesteImg.this, "Selecione uma imagem primeiro.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // A imagem foi selecionada com sucesso
            mSelectedImageUri = data.getData();

            try {
                // Exiba a imagem selecionada no ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedImageUri);
                mImgPhoto.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (mSelectedImageUri != null) {
            // Gere um nome de arquivo exclusivo para a imagem
            imageFileName = UUID.randomUUID().toString() + ".jpg";

            // Converte a imagem em um array de bytes
            Bitmap bitmap = ((BitmapDrawable) mImgPhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] data = baos.toByteArray();

            // Salvar a imagem localmente
            saveImageLocally(data, imageFileName);

            // Faça o upload da imagem para o Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageFileName);
            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Imagem enviada com sucesso
                    Toast.makeText(TelaTesteImg.this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Tratamento de erro
                    Log.e("FirebaseStorage", "Erro ao fazer upload da imagem: " + e.getMessage(), e);
                    Toast.makeText(TelaTesteImg.this, "Erro ao carregar a imagem.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveImageLocally(byte[] imageData, String fileName) {
        try {
            File directory = getFilesDir();
            File file = new File(directory, fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(imageData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
