package com.example.learningstrenghtaaron.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.learningstrenghtaaron.BaseDeDatos.Firestore;
import com.example.learningstrenghtaaron.Entidades.Usuario;
import com.example.learningstrenghtaaron.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class PerfilUsuarioActivity extends AppCompatActivity {
    private TextInputLayout tilUsuario, tilNombre, tilEmail, tilFecha, tilPeso, tilAltura;
    private TextInputEditText txtUsuario, txtNombre, txtEmail, txtFecha, txtPeso, txtAltura;
    private FloatingActionButton btnAtras, btnAceptar;
    private MaterialButton btnCambiarFoto;
    private Usuario usuario;
    private Firestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        // TODO: recoger datos del usuario de la bd

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = Firestore.getInstance();

        inicializarComponentes();

        listeners();
        rellenarDatosUsuario(user.getUid());

    }

    private void inicializarComponentes() {
        tilUsuario = findViewById(R.id.tilNombreUsuarioPerfilUsuario);
        txtUsuario = findViewById(R.id.txtNombreUsuarioPerfilUsuario);
        tilNombre = findViewById(R.id.tilNombrePerfilUsuario);
        txtNombre = findViewById(R.id.txtNombrePerfilUsuario);
        tilEmail = findViewById(R.id.tilEmailPerfilUsuario);
        txtEmail = findViewById(R.id.txtEmailPerfilUsuario);
        tilFecha = findViewById(R.id.tilFechaNacPerfilUsuario);
        txtFecha = findViewById(R.id.txtFechaNacPerfilUsuario);
        tilPeso = findViewById(R.id.tilPesoPerfilUsuario);
        txtPeso = findViewById(R.id.txtPesoPerfilUsuario);
        tilAltura = findViewById(R.id.tilAlturaPerfilUsuario);
        txtAltura = findViewById(R.id.txtAlturaPerfilUsuario);

        btnAtras = findViewById(R.id.btnAtrasPerfilUsuario);
        btnAceptar = findViewById(R.id.btnAceptarPerfilUsuario);
        btnCambiarFoto = findViewById(R.id.btnCambiarFotoPerfilUsuario);
    }

    private void listeners() {

        btnAtras.setOnClickListener(view -> finish());

        btnAceptar.setOnClickListener(view -> modificarDatosUsuario());

        btnCambiarFoto.setOnClickListener(view -> cambiarfoto());

        txtUsuario.setOnFocusChangeListener((view, b) -> {
            if (!b && txtUsuario.getText().toString().isBlank())txtUsuario.setError("Tienes que escribir un nombre de usuario"); else txtUsuario.setError(null);
        });
        txtNombre.setOnFocusChangeListener((view, b) -> {
            if (!b && txtNombre.getText().toString().isBlank())txtNombre.setError("Tienes que escribir tu nombre"); else txtNombre.setError(null);
        });
        txtEmail.setOnFocusChangeListener((view, b) -> {
            if (!b && txtEmail.getText().toString().isBlank())txtEmail.setError("Tienes que escribir tu email"); else txtEmail.setError(null);
        });

        txtFecha.setOnFocusChangeListener((view, b) -> {
            if (b) showDatePickerDialog();
        });
    }

    private void rellenarDatosUsuario(String uid) {
        usuario = firestore.getUsuario(uid);

        txtUsuario.setText(usuario.getUsuario() == null ? "" : usuario.getUsuario());
        txtNombre.setText(usuario.getDeporte() == null ? "" : usuario.getDeporte());
        txtFecha.setText(usuario.getFechaNac() == null ? "" : usuario.getFechaNac());
        txtPeso.setText(String.format("%s", usuario.getPeso() == 0 ? "" : usuario.getPeso()));
        txtAltura.setText(String.format("%s", usuario.getAltura() == 0 ? "" : usuario.getAltura()));
    }

    private void modificarDatosUsuario() {
        if (chequearDatos()) {
            recogerDatosUsuario();
            // TODO: actualizar datos usuario en la bd, los campos peso y altura pueden ser nulos
            Map<String, Object> map = usuario.toMap();
            map.put("sdljf", 79);
            firestore.insertarUsuario(map);

        } else {
            Toast.makeText(this, "Los campos marcados con * son obligatorios.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean chequearDatos() {
        if (!txtUsuario.getText().toString().trim().isBlank()
                && !txtEmail.getText().toString().trim().isBlank() && !txtFecha.getText().toString().trim().isBlank()) {
            return true;
        }
        return false;
    }

    private void recogerDatosUsuario() {
        // TODO: los getText pueden ser nulos
        usuario.setUsuario(txtUsuario.getText().toString().trim());
        usuario.setCorreo(txtEmail.getText().toString().trim());
        usuario.setFechaNac(txtFecha.getText().toString().trim());
        usuario.setPeso(Long.parseLong(txtPeso.getText().toString().trim()));
        usuario.setAltura(Long.parseLong(txtAltura.getText().toString().trim()));
    }

    private void cambiarfoto() {
        //TODO: cambiar foto
    }

    private void showDatePickerDialog() {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Fecha nacimiento").build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> txtFecha.setText("" + materialDatePicker.getHeaderText()));

        materialDatePicker.addOnDismissListener(dialogInterface -> txtFecha.clearFocus());

        materialDatePicker.show(getSupportFragmentManager(), "TAG");
    }
}