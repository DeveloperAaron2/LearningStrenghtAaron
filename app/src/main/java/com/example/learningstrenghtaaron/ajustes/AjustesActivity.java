package com.example.learningstrenghtaaron.ajustes;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class AjustesActivity extends AppCompatActivity {
    private MaterialButton btnTema, btnCorreo, btnPass, btnCuenta;
    private Firestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        firestore = Firestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        inicializarComponentes();
        onClickBotones();
    }

    private void onClickBotones() {
        btnTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir fragment con varios temas para cambiar
            }
        });
        btnCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrar fragment donde introducir el correo
                mAuth.getCurrentUser().updateEmail("New email");
                mAuth.getCurrentUser().sendEmailVerification();
                firestore.actualizarUsuario(firestore.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid()));
            }
        });
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si se acuerda de la contraseña
                mAuth.getCurrentUser().updatePassword("New password");
                //Si no se acuerda de la contraseña
                mAuth.sendPasswordResetEmail("Email new password");

                firestore.actualizarUsuario(firestore.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid()));
            }
        });
        btnCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar confirmacion
                AlertDialog.Builder builder = new AlertDialog.Builder(AjustesActivity.this);
                builder.setTitle("Eliminar cuenta definitivamente");
                builder.setMessage("¿Estas seguro de que deseas eliminar tu cuenta y todos tus datos definitivamente?");
                builder.setPositiveButton("Si, soy un flaco", (dialogInterface, i) -> {
                    firestore.borrarUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mAuth.getCurrentUser().delete();
                });
                builder.setNegativeButton("No, estoy mas fuerte que el vinagre", (dialogInterface, i) ->
                        Toast.makeText(AjustesActivity.this, "Asi me gusta", Toast.LENGTH_SHORT).show());
                builder.show();
            }
        });
    }

    private void inicializarComponentes() {
        btnTema = findViewById(R.id.btnCambiarTemaAjustesActivity);
        btnCorreo = findViewById(R.id.btnCambiarCorreoAjustesActivity);
        btnPass = findViewById(R.id.btnCambiarContraseniaAjustesActivity);
        btnCuenta = findViewById(R.id.btnEliminarCuentaAjustesActivity);
    }
}