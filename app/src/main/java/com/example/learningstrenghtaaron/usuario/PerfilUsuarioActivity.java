package com.example.learningstrenghtaaron.usuario;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class PerfilUsuarioActivity extends AppCompatActivity {
    private TextInputEditText txtUsuario, txtDeporte, txtFechaNac, txtPeso, txtAltura;
    private FloatingActionButton btnAtras, btnAceptar;
    private MaterialButton btnCambiarFoto;
    private Usuario usuario;
    private Firestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        //Inicializamos user y Firestore
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = Firestore.getInstance();
        //Inicializamos componentes y listeners
        inicializarComponentes();
        listeners();
        //Rellenamos con datos del usuario
        rellenarDatosUsuario(user.getUid());
    }

    private void inicializarComponentes() {
        //Txt
        txtUsuario = findViewById(R.id.txtNombreUsuarioPerfilUsuario);
        txtDeporte = findViewById(R.id.txtDeportePerfilUsuario);
        txtFechaNac = findViewById(R.id.txtFechaNacPerfilUsuario);
        txtPeso = findViewById(R.id.txtPesoPerfilUsuario);
        txtAltura = findViewById(R.id.txtAlturaPerfilUsuario);
        //Btn
        btnAtras = findViewById(R.id.btnAtrasPerfilUsuario);
        btnAceptar = findViewById(R.id.btnAceptarPerfilUsuario);
        btnCambiarFoto = findViewById(R.id.btnCambiarFotoPerfilUsuario);
    }

    private void listeners() {
        //Listeners botones
        btnAtras.setOnClickListener(view -> finish());
        btnAceptar.setOnClickListener(view -> modificarDatosUsuario());
        btnCambiarFoto.setOnClickListener(view -> cambiarfoto());
        //Errores campos obligatorios
        txtUsuario.setOnFocusChangeListener((view, b) -> {
            if (!b && txtUsuario.getText().toString().trim().isBlank())txtUsuario.setError("Tienes que escribir un nombre de usuario"); else txtUsuario.setError(null);
        });
        txtFechaNac.setOnFocusChangeListener((view, b) -> {
            if (b) showDatePickerDialog(); else if(txtFechaNac.getText().toString().trim().isBlank())txtFechaNac.setError("Tienes que seleccionar tu fecha de nacimiento");else txtFechaNac.setError(null);
        });
    }

    private void rellenarDatosUsuario(String uid) {
        usuario = firestore.getUsuario(uid);
        txtUsuario.setText(usuario.getUsuario() == null ? "" : usuario.getUsuario());
        txtDeporte.setText(usuario.getDeporte() == null ? "" : usuario.getDeporte());
        txtFechaNac.setText(usuario.getFechaNac() == null ? "" : usuario.getFechaNac());
        txtPeso.setText(String.format("%s", usuario.getPeso() == 0 ? "" : usuario.getPeso()));
        txtAltura.setText(String.format("%s", usuario.getAltura() == 0 ? "" : usuario.getAltura()));
    }

    private void modificarDatosUsuario() {
        if (chequearDatos()) {
            recogerDatosUsuario();
            // TODO: actualizar datos usuario en la bd, los campos peso y altura pueden ser nulos
            Map<String, Object> map = usuario.toMap();
            firestore.insertarUsuario(map);

        } else {
            Toast.makeText(this, "Los campos marcados con * son obligatorios.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean chequearDatos() {
        if (!txtUsuario.getText().toString().trim().isBlank() && !txtFechaNac.getText().toString().trim().isBlank()) {
            return true;
        }
        return false;
    }

    private void recogerDatosUsuario() {
        // TODO: los getText pueden ser nulos
        usuario.setUsuario(txtUsuario.getText().toString().trim());
        usuario.setDeporte(txtDeporte.getText().toString().trim());
        usuario.setFechaNac(txtFechaNac.getText().toString().trim());
        usuario.setPeso(Long.parseLong(txtPeso.getText().toString().trim()));
        usuario.setAltura(Long.parseLong(txtAltura.getText().toString().trim()));
    }

    private void cambiarfoto() {
        //TODO: cambiar foto
    }

    private void showDatePickerDialog() {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Fecha nacimiento").build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> txtFechaNac.setText(materialDatePicker.getHeaderText()));
        materialDatePicker.addOnDismissListener(dialogInterface -> txtFechaNac.clearFocus());
        materialDatePicker.show(getSupportFragmentManager(), "TAG");
    }
}