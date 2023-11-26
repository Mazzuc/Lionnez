package com.example.cad_login_fb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
        Button closeButton = view.findViewById(R.id.btnClose);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String feedbackMessage = feedbackEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || feedbackMessage.isEmpty()) {
                    Toast.makeText(requireContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(name, email, feedbackMessage);
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para fechar o fragmento
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.getSupportFragmentManager().popBackStack();
                    Log.d("FeedbackFragment", "Botão fechar clicado");
                }
            }
        });

        return view;
    }

    private void sendEmail(String name, String email, String feedbackMessage) {
        String[] TO = {"seu@email.com"}; // Substitua pelo seu endereço de email
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback do App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nome: " + name + "\nEmail: " + email + "\n\n" + feedbackMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done) {
            message("Done");
        }

        return super.onOptionsItemSelected(item);
    }

    public void message(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
    }
}
