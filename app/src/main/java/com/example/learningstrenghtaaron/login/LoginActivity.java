package com.example.learningstrenghtaaron.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.learningstrenghtaaron.PantallaPrincipal;
import com.example.learningstrenghtaaron.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    Button btnLogin;
    SignInButton btnGoogle;
    TextInputEditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        googleSignIn();

        email = findViewById(R.id.editTextCorreoLogin);
        password = findViewById(R.id.editTextContrasenaLogin);
        btnLogin = findViewById(R.id.btnIngresarLogin);
        btnGoogle = findViewById(R.id.btnGoogleLogin);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                /* TODO: Revisar si se mete un correo de hotmail u outlook*/
                if (!emailUser.contains("@gmail.com")) {
                    emailUser = emailUser.concat("@gmail.com");
                }
                String passUser = password.getText().toString().trim();

                if (emailUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Tienes que ingresar correo y contraseña.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signOut();
                    loginUserEmail(emailUser, passUser);
                }
            }
        });

        btnGoogle.setOnClickListener(view -> {
            mAuth.signOut();
            loginUserGoogle();
        });
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void loginUserGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Error al iniciar sesion con google: " + e.getLocalizedMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Inicio de sesion con credencial correcto.");
                            Toast.makeText(LoginActivity.this, "Inicio de sesion con credencial correcto", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(LoginActivity.this, PantallaPrincipal.class));
                        } else {
                            Log.w(TAG, "Error al iniciar sesion con credencial: " + task.getException());
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Fallo al iniciar sesion con credencial: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUserEmail(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkEmailVerified();
                } else {
                    Log.w(TAG, "Error al iniciar sesion con email y contraseña: " + task.getException());
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Fallo al iniciar sesion con email y contraseña: " + e.getMessage());
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailVerified() {
        if (mAuth.getCurrentUser().isEmailVerified()) {
            finish();
            startActivity(new Intent(LoginActivity.this, PantallaPrincipal.class));
            Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signOut();
            Toast.makeText(LoginActivity.this, "Antes de iniciar sesion tienes que verificar tu correo.", Toast.LENGTH_SHORT).show();
        }
    }
}