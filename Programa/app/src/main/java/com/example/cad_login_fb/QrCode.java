package com.example.cad_login_fb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrCode extends Fragment {

    private DecoratedBarcodeView barcodeView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private boolean cameraPermissionGranted = false;
    private boolean isScanningAllowed = true;

    private AlertDialog alertDialog;

    public QrCode() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);
        barcodeView = view.findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermissionGranted = true;
            initCameraAsync();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void initCameraAsync() {
        if (cameraPermissionGranted) {
            barcodeView.resume();
            barcodeView.decodeContinuous(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermissionGranted = true;
                initCameraAsync();
            } else {
                cameraPermissionGranted = false;
                Toast.makeText(requireContext(), "A permissão da câmera é necessária para usar esta funcionalidade.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        barcodeView.pauseAndWait();
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (isScanningAllowed) {
                isScanningAllowed = false;

                String nomeAnimal = result.getText();
                buscarAnimalNaApi(nomeAnimal);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isScanningAllowed = true;
                    }
                }, 60 * 1000); // 60 segundos em milissegundos
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            // Atualize a interface do usuário conforme necessário
        }
    };

    private void buscarAnimalNaApi(String nomeAnimal) {
        ApiServiceCard apiServiceCard = ApiClientCard.getClient().create(ApiServiceCard.class);
        Call<AnimalCard> call = apiServiceCard.getAnimalByNome(nomeAnimal);

        call.enqueue(new Callback<AnimalCard>() {
            @Override
            public void onResponse(Call<AnimalCard> call, Response<AnimalCard> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exibirAlertDialog(response.body());
                } else {
                    exibirMensagemErro("Animal não encontrado");
                }
            }

            @Override
            public void onFailure(Call<AnimalCard> call, Throwable t) {
                exibirMensagemErro("Falha na requisição. Verifique sua conexão com a internet.");
            }
        });
    }

    private void exibirAlertDialog(AnimalCard animalCard) {
        if (!isDetached() && !isRemoving()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.TransparentDialog);

            View view = getLayoutInflater().inflate(R.layout.custom_dialog_apicards, null);
            TextView txtnome = view.findViewById(R.id.txtnome);
            TextView txtdescricao = view.findViewById(R.id.txtdescricao);
            TextView txttipo = view.findViewById(R.id.txttipo);
            LinearLayout linearImage = view.findViewById(R.id.LinearImage);
            LinearLayout linearSaibaMais = view.findViewById(R.id.LinearSaibaMais);
            LinearLayout linearClose = view.findViewById(R.id.LinearClose); // Retrieve LinearClose

            txtnome.setText(animalCard.getNome());
            txtdescricao.setText(animalCard.getHabitat());
            txttipo.setText(animalCard.getTipoAnim());

            new LoadImageTask(linearImage).execute(animalCard.getImagem());

            linearSaibaMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirDetalhesActivity(animalCard);
                }
            });

            // Set an OnClickListener for LinearClose
            linearClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss the AlertDialog when LinearClose is clicked
                    alertDialog.dismiss();
                }
            });

            builder.setView(view);

            alertDialog = builder.create(); // Store the AlertDialog in the class variable
            alertDialog.setCanceledOnTouchOutside(true);

            alertDialog.show();
        }
    }

    private void abrirDetalhesActivity(AnimalCard animalCard) {
        Intent intent = new Intent(requireContext(), DetalhesActivity.class);
        intent.putExtra("title", animalCard.getNome());
        intent.putExtra("description", animalCard.getHabitat());
        intent.putExtra("habitat", animalCard.getHabitat());
        intent.putExtra("pais", animalCard.getPais());
        intent.putExtra("alimentacao", animalCard.getAlimentacao());
        intent.putExtra("peso", animalCard.getPeso());
        intent.putExtra("altura", animalCard.getAltura());
        intent.putExtra("curiosidades", animalCard.getCuriosidades());
        intent.putExtra("imagem", animalCard.getImagem());

        startActivity(intent);
    }

    private void exibirMensagemErro(String mensagem) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final LinearLayout linearLayout;

        LoadImageTask(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            try {
                return BitmapFactory.decodeStream(new java.net.URL(url).openStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                Drawable roundedImage = getRoundedBitmapDrawable(result);
                linearLayout.setBackground(roundedImage);
            }
        }

        private Drawable getRoundedBitmapDrawable(Bitmap bitmap) {
            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(linearLayout.getResources(), bitmap);
            roundedDrawable.setCornerRadius(30 * linearLayout.getResources().getDisplayMetrics().density);

            GradientDrawable borderDrawable = new GradientDrawable();
            borderDrawable.setColor(Color.TRANSPARENT);
            borderDrawable.setShape(GradientDrawable.RECTANGLE);
            borderDrawable.setCornerRadii(new float[]{
                    30 * linearLayout.getResources().getDisplayMetrics().density, 30 * linearLayout.getResources().getDisplayMetrics().density,
                    30 * linearLayout.getResources().getDisplayMetrics().density, 30 * linearLayout.getResources().getDisplayMetrics().density,
                    0, 0, 0, 0
            });

            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{borderDrawable, roundedDrawable});

            return layerDrawable;
        }
    }
}



