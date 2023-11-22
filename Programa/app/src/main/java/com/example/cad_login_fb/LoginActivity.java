package com.example.cad_login_fb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FacebookAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        binding.textcadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textrecuperarconta.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperarContaActivity.class));
        });

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());

        // Configurar "LinearVoltar"
        LinearLayout linearVoltar = findViewById(R.id.LinearVoltar);
        linearVoltar.setOnClickListener(view -> onVoltarLayoutClick());

        // Configurar o cliente GoogleSignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Configurar o clique do LinearLayout de login do Facebook
        LinearLayout linearFacebook = findViewById(R.id.LinearFace);
        linearFacebook.setOnClickListener(v -> loginComFacebook());

        LinearLayout linearGoogle = findViewById(R.id.LinearGoogle);
        linearGoogle.setOnClickListener(v -> loginComGoogle());
    }

    public void onVoltarLayoutClick() {
        finish();
    }

    private void ValidaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();

        if (!email.isEmpty() && !senha.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            LoginFireBase(email, senha);
        } else {
            showToast("Informe um email e senha válidos.");
        }
    }

    private void LoginFireBase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setLoginStatus(true);
                finish();
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                binding.progressBar.setVisibility(View.GONE);
                showToast("Opa, verifique as informações: ocorreu um erro.");
            }
        });
    }

    private void showToast(String message) {
        ActivityCustonToastBinding customToastBinding = ActivityCustonToastBinding.inflate(getLayoutInflater());
        customToastBinding.textView.setText(message);

        Toast customToast = new Toast(this);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastBinding.getRoot());
        customToast.show();
    }

    private void setLoginStatus(boolean isLogged) {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }

    private void loginComFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Sucesso ao logar com Facebook!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Erro no login com Facebook: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        setLoginStatus(true);
                        finish();
                        startActivity(new Intent(this, HomeActivity.class));
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        showToast("Opa, verifique as informações: ocorreu um erro.");
                    }
                });
    }

    private void loginComGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.w("GoogleSignIn", "Google sign in failed", e);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        setLoginStatus(true);
                        finish();
                        startActivity(new Intent(this, HomeActivity.class));
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        showToast("Opa, verifique as informações: ocorreu um erro.");
                    }
                });
    }
}
