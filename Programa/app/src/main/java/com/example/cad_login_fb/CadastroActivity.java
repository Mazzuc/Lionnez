package com.example.cad_login_fb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cad_login_fb.databinding.ActivityCadastroBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToastBinding;
import com.example.cad_login_fb.databinding.ActivityCustonToatAlertBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Inicializar o CallbackManager
        callbackManager = CallbackManager.Factory.create();

        // Configurar o clique do LinearLayout de login do Facebook
        LinearLayout linearFacebook = findViewById(R.id.LinearFace);
        linearFacebook.setOnClickListener(v -> loginComFacebook());

        // Configurar o clique do LinearLayout de login do Google
        LinearLayout linearGoogle = findViewById(R.id.LinearGoogle);
        linearGoogle.setOnClickListener(v -> loginComGoogle());

        binding.btnCriarConta.setOnClickListener(v -> ValidaDados());

        // Configurar o cliente GoogleSignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        LinearLayout linearVoltar = findViewById(R.id.LinearVoltar);
        linearVoltar.setOnClickListener(view -> onVoltarLayoutClick());
    }

    public void onVoltarLayoutClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificar se o usuário já está logado ao retomar a atividade
        checkIfUserIsLoggedIn();
        // Deslogar o usuário do Facebook ao retomar a atividade
        LoginManager.getInstance().logOut();
    }

    private void checkIfUserIsLoggedIn() {
        // Verificar se o usuário está logado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && isUserFullyAuthenticated()) {
            // O usuário está totalmente autenticado, vá para HomeActivity
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    private boolean isUserFullyAuthenticated() {
        // Verificar se o usuário está totalmente autenticado usando SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getBoolean("user_logged_in", false);
    }

    private void loginComFacebook() {
        if (mAuth.getCurrentUser() != null) {
            // O usuário já está autenticado, vá para HomeActivity
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(CadastroActivity.this, "Sucesso ao logar com Facebook!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(CadastroActivity.this, "Erro no login com Facebook: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Verificar se o usuário está autenticado
                        if (user != null) {
                            // Verificar se o usuário já tem uma imagem de perfil no Firebase
                            if (user.getPhotoUrl() == null) {
                                // Se não houver imagem de perfil no Firebase, obtenha a imagem do Facebook
                                getFacebookUserProfileImage(token, user.getUid());
                            }

                            // Salvar informações temporariamente
                            saveUserInfoLocally(user.getDisplayName());

                            // Deslogar o usuário do Facebook ao retomar a atividade
                            LoginManager.getInstance().logOut();

                            // Redirecionar para a HomeActivity
                            finish();
                            startActivity(new Intent(this, HomeActivity.class));
                        } else {
                            // Se o usuário não está autenticado, exiba uma mensagem de erro
                            Toast.makeText(CadastroActivity.this, "Erro ao autenticar usuário", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Se o login falhar, exiba uma mensagem para o usuário.
                        String errorMessage = "Erro no login com Facebook.";
                        if (task.getException() != null) {
                            errorMessage += " " + task.getException().getMessage();
                        }
                        Toast.makeText(CadastroActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getFacebookUserProfileImage(AccessToken token, String userId) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                (object, response) -> {
                    try {
                        if (object.has("picture")) {
                            JSONObject pictureObject = object.getJSONObject("picture");
                            JSONObject dataObject = pictureObject.getJSONObject("data");
                            String profileImageUrl = dataObject.getString("url");

                            // Salvar a imagem de perfil no Firebase Storage
                            saveProfileImageToFirebaseStorage(userId, profileImageUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void loginComGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

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

                        // Verificar se o usuário está autenticado
                        if (user != null) {
                            // Verificar se o usuário já tem uma imagem de perfil no Firebase
                            if (user.getPhotoUrl() == null) {
                                // Se não houver imagem de perfil no Firebase, obtenha a imagem do Google
                                saveProfileImageToFirebaseStorage(user.getUid(), user.getPhotoUrl().toString());
                            }

                            // Salvar informações temporariamente
                            saveUserInfoLocally(user.getDisplayName());

                            // Redirecionar para a HomeActivity
                            finish();
                            startActivity(new Intent(this, HomeActivity.class));
                        } else {
                            // Se o usuário não está autenticado, exiba uma mensagem de erro
                            Toast.makeText(CadastroActivity.this, "Erro ao autenticar usuário", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Se o login falhar, exiba uma mensagem para o usuário.
                        String errorMessage = "Erro no login com Google.";
                        if (task.getException() != null) {
                            errorMessage += " " + task.getException().getMessage();
                        }
                        Toast.makeText(CadastroActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveProfileImageToFirebaseStorage(String userId, String profileImageUrl) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference profileImageRef = storageRef.child("profile_images").child(userId + ".jpg");

        // Converta a URL da imagem para Uri
        Uri profileImageUri = Uri.parse(profileImageUrl);

        // Faça o upload da imagem para o Firebase Storage
        profileImageRef.putFile(profileImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Imagem de perfil salva com sucesso
                    Log.d("FirebaseStorage", "Imagem de perfil salva com sucesso");
                })
                .addOnFailureListener(e -> {
                    // Falha ao salvar a imagem de perfil
                    Log.e("FirebaseStorage", "Falha ao salvar a imagem de perfil: " + e.getMessage());
                });
    }

    private void saveUserInfoLocally(String displayName) {
        // Adicione aqui a lógica para salvar as informações localmente
        saveAuthenticationState(); // Adicionado para salvar o estado de autenticação
    }

    private String retrieveUserInfoLocally() {
        // Adicione aqui a lógica para recuperar as informações armazenadas localmente
        return "";
    }

    private void ValidaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editsenha.getText().toString().trim();
        String nome = binding.editNome.getText().toString().trim();

        if (nome.isEmpty()) {
            // Toast.makeText(this, "Informe seu nome", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe seu nome");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else if (email.isEmpty()) {
            //   Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe seu E-mail");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else if (senha.isEmpty()) {
            //  Toast.makeText(this, "Informe uma senha", Toast.LENGTH_SHORT).show();
            ActivityCustonToatAlertBinding customToastBindingCorrect = ActivityCustonToatAlertBinding.inflate(getLayoutInflater());

            // Personalize o texto da mensagem do Toast
            customToastBindingCorrect.textViewalerta.setText("Informe uma senha");

            Toast customToast = new Toast(this);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(customToastBindingCorrect.getRoot());
            customToast.show();
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            CriarContaFireBase(email, senha, nome);
        }
    }

    private void CriarContaFireBase(String email, String senha, String nome) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registro com sucesso
                        FirebaseUser user = mAuth.getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nome)
                                .build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                // Redirecionar para a HomeActivity
                                finish();
                                startActivity(new Intent(this, HomeActivity.class));
                            } else {
                                binding.progressBar.setVisibility(View.GONE);
                                // Toast.makeText(this, "Opa, ocorreu um erro ao atualizar o nome", Toast.LENGTH_SHORT).show();
                                // Código para mostrar o Toast personalizado
                                ActivityCustonToastBinding customToastBinding = ActivityCustonToastBinding.inflate(getLayoutInflater());

                                // Personalize o texto da mensagem do Toast
                                customToastBinding.textView.setText("Opa, verifique as informações: ocorreu um erro.");

                                Toast customToast = new Toast(this);
                                customToast.setDuration(Toast.LENGTH_SHORT);
                                customToast.setView(customToastBinding.getRoot());
                                customToast.show();
                            }
                        });
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        //  Toast.makeText(this, "Opa, verifique as informações: ocorreu um erro", Toast.LENGTH_SHORT).show();

                        // Código para mostrar o Toast personalizado
                        ActivityCustonToastBinding customToastBinding = ActivityCustonToastBinding.inflate(getLayoutInflater());

                        // Personalize o texto da mensagem do Toast
                        customToastBinding.textView.setText("Opa, verifique as informações: ocorreu um erro.");

                        Toast customToast = new Toast(this);
                        customToast.setDuration(Toast.LENGTH_SHORT);
                        customToast.setView(customToastBinding.getRoot());
                        customToast.show();
                    }
                });
    }

    private void saveAuthenticationState() {
        // Salvar o estado de autenticação usando SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("user_logged_in", true);
        editor.apply();
    }
}

