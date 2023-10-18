package com.example.cad_login_fb;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaPerfilActivity extends AppCompatActivity {

    private TextView emailTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        emailTextView = findViewById(R.id.emailTextView);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail(); // Obtém o email do usuário autenticado
            if (userEmail != null) {
                emailTextView.setText("Email: " + userEmail);
            }
        }
    }
}
