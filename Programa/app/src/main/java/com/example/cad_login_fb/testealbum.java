package com.example.cad_login_fb;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class testealbum extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText nameEditText;
    private com.google.android.material.textfield.TextInputEditText emailEditText;
    private com.google.android.material.textfield.TextInputEditText feedbackEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testedeco);

        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        feedbackEditText = findViewById(R.id.editTextFeedback);
        Button sendButton = findViewById(R.id.buttonSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String feedbackMessage = feedbackEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || feedbackMessage.isEmpty()) {
                    Toast.makeText(testealbum.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(name, email, feedbackMessage);
                }
            }
        });
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
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item_done){
            message("Done");

        }

        return super.onOptionsItemSelected(item);
    }

    public void message(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
