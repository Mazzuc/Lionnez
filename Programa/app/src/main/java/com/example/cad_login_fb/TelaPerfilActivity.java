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
import java.io.IOException;
import java.util.UUID;

public class TelaPerfilActivity extends AppCompatActivity {

    private Button mBtnSelectPhoto;
    private ImageView mImg_Photo;
    private Uri mSelectUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testes);

        mBtnSelectPhoto = findViewById(R.id.btn_selectfoto);
        mImg_Photo = findViewById(R.id.img_photo);

        mBtnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            mSelectUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectUri);
                mImg_Photo.setImageDrawable(new BitmapDrawable(bitmap));
                mBtnSelectPhoto.setAlpha(0);

                // Após selecionar a imagem, chame o método para salvar no Firebase
                savePhotoToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void savePhotoToFirebase() {
        if (mSelectUri != null) {
            // Gere um nome de arquivo exclusivo para a imagem
            String filename = UUID.randomUUID().toString();
            String path = "images/" + filename; // Caminho para a pasta "images"

            // Crie a referência para o arquivo
            final StorageReference ref = FirebaseStorage.getInstance().getReference().child(path);

            // Converte a imagem em um array de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) mImg_Photo.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] data = baos.toByteArray();

            // Faça o upload da imagem para o Firebase Storage
            UploadTask uploadTask = ref.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Imagem enviada com sucesso
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.i("FirebaseStorage", "Imagem carregada: " + uri.toString());
                            Toast.makeText(TelaPerfilActivity.this, "Imagem carregada com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Tratamento de erro
                    Log.e("FirebaseStorage", "Erro ao fazer upload da imagem: " + e.getMessage(), e);
                    Toast.makeText(TelaPerfilActivity.this, "Erro ao carregar a imagem.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}