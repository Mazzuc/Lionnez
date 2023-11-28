package com.example.cad_login_fb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class Fragment_Feedback extends Fragment implements BaseFragment {

    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText feedbackEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__feedback, container, false);

        nameEditText = view.findViewById(R.id.editTextName);
        emailEditText = view.findViewById(R.id.editTextEmail);
        feedbackEditText = view.findViewById(R.id.editTextFeedback);
        Button sendButton = view.findViewById(R.id.buttonSend);
        Button btnClose = view.findViewById(R.id.btnClose);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String feedbackMessage = feedbackEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || feedbackMessage.isEmpty()) {
                    // Mostrar Toast personalizado com mensagens específicas
                    showCustomToast("Por favor, preencha todos os campos");
                } else {
                    sendEmail(name, email, feedbackMessage);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fechar o Fragment_Feedback
                removeFragment();
            }
        });

        return view;
    }

    private void sendEmail(String name, String email, String feedbackMessage) {
        String[] TO = {"lionnez.empresa@email.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback do App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nome: " + name + "\nEmail: " + email + "\n\n" + feedbackMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar e-mail..."));
            // Mostrar Toast de sucesso
            showSuccessToast();
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            // Adicione a lógica de tratamento de erro aqui, se desejar
        }
    }

    private void removeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_custon_toat_alert, null);

        // Configurar TextView do Toast
        // Este é apenas um exemplo, ajuste conforme necessário
        Toast toast = new Toast(requireContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void showSuccessToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_custon_toast_correct, null);

        // Configurar TextView do Toast
        // Este é apenas um exemplo, ajuste conforme necessário
        Toast toast = new Toast(requireContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
