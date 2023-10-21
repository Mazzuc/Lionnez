package com.example.cad_login_fb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.storage.FileDownloadTask;
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            mSelectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedImageUri);
                mImgPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (mSelectedImageUri != null) {
            imageFileName = UUID.randomUUID().toString() + ".jpg";
            Bitmap bitmap = ((BitmapDrawable) mImgPhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] data = baos.toByteArray();
            saveImageLocally(data, imageFileName);

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageFileName);
            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TelaTesteImg.this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                    downloadAndDisplayImage();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
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

    private void downloadAndDisplayImage() {
        if (imageFileName != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageFileName);
            File localFile = new File(getFilesDir(), imageFileName);
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                    mImgPhoto.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    Log.e("FirebaseStorage", "Erro ao baixar a imagem: " + e.getMessage(), e);
                    Toast.makeText(TelaTesteImg.this, "Erro ao baixar a imagem.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
