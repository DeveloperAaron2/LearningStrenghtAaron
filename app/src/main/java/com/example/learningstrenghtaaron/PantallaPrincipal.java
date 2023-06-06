package com.example.learningstrenghtaaron;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.calculadoras.macros.CalculadoraMacrosFragment;
import com.example.learningstrenghtaaron.databinding.ActivityPantallaPrincipalBinding;
import com.example.learningstrenghtaaron.entidades.Usuario;
import com.example.learningstrenghtaaron.login.MainActivity;
import com.example.learningstrenghtaaron.rutinas.RutinasFragment;
import com.example.learningstrenghtaaron.usuario.PerfilUsuarioFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PantallaPrincipal extends AppCompatActivity {
    FirebaseAuth mAuth;
    ActivityPantallaPrincipalBinding binding;
    Firestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPantallaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RutinasFragment());
        mAuth = FirebaseAuth.getInstance();
        firestore = Firestore.getInstance();
        if (mAuth.getCurrentUser() != null) recogerUsuario(mAuth.getCurrentUser().getUid());
        //Botones de navegación
        binding.bottomNavigationViewPantallaPrincipal.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Rutinas:
                    replaceFragment(new RutinasFragment());
                    break;
                case R.id.Calculadoras:
//                    startActivity(new Intent(PantallaPrincipal.this, CalculadoraMacrosActivity.class));
                    replaceFragment(new CalculadoraMacrosFragment());
                    break;
                case R.id.Perfil:
                    if (mAuth.getCurrentUser().isAnonymous()) {
                        startActivity(new Intent(PantallaPrincipal.this, MainActivity.class));
                    } else {
                        replaceFragment(new PerfilUsuarioFragment(firestore.getUsuario()));
                    }
                    break;
            }
            return true;
        });
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finishAffinity();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutPantallaPrincipal, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            signInAnonymous();
        }
    }

    private Usuario recogerUsuario(String uid) {
        return firestore.getUsuario(uid);
    }

    private void signInAnonymous() {
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PantallaPrincipal.this, "Signed in Anonymously", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PantallaPrincipal.this, MainActivity.class));
                } else {
                    Toast.makeText(PantallaPrincipal.this, "Not Succesful. " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PantallaPrincipal.this, "Error entrando anonimamente." + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG, "MainActivity. " + e.getMessage());
            }
        });
    }
}