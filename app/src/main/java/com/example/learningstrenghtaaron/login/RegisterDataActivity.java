package com.example.learningstrenghtaaron.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.learningstrenghtaaron.BaseDeDatos.Firestore;
import com.example.learningstrenghtaaron.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // TODO: cambiar edad por fecha de nacimiento
    private TextInputLayout tilRm1, tilRm2, tilRm3;
    private TextInputEditText txtUsuario, txtFecha, txtPeso, txtAltura, txtRm1, txtRm2, txtRm3;
    private Spinner spinnerDeporte;
    private LinearLayout layoutRm;
    private Button btnEntrar;
    private List<String> listaRms;
    private Firestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        listaRms = new ArrayList<>();
        firestore = Firestore.getInstance();

        inicializarTxt();

        inicializarSpinner();

        listeners();
    }

    private void setRm(String rm1, String rm2, String rm3, String tipo) {
        tilRm1.setSuffixText(tipo);
        tilRm2.setSuffixText(tipo);
        tilRm3.setSuffixText(tipo);

        tilRm1.setHint(rm1);
        tilRm2.setHint(rm2);
        tilRm3.setHint(rm3);
    }

    private void listeners() {
        btnEntrar = findViewById(R.id.btnEntrarRegisterData);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> mapDatosUsuario = recogerDatosTv();
                if (mapDatosUsuario.get("Usuario").toString().isEmpty() || mapDatosUsuario.get("FechaNac").toString().isEmpty()) {
                    Toast.makeText(RegisterDataActivity.this, "Por favor, rellene los campos marcados con un *.", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Subir fecha como date a bd
                    subirABd(mapDatosUsuario);
                }
            }
        });

        txtFecha.setOnFocusChangeListener((view, b) -> {
            if (b) showDatePickerDialog();
        });
    }

    private void subirABd(Map<String, Object> mapDatosUsuario) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        mapDatosUsuario.put("Id", id);
        mapDatosUsuario.put("Correo", user.getEmail());
        mapDatosUsuario.put("Foto", "");

        firestore.insertarUsuario(mapDatosUsuario);
        irALogin();
    }

    private void irALogin() {
        enviarCorreoVerificacion();
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(RegisterDataActivity.this, LoginActivity.class));
    }

    private void enviarCorreoVerificacion() {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterDataActivity.this, "Te hemos enviado un correo de verificacion, verifica el correo antes de iniciar sesion.", Toast.LENGTH_LONG).show();
                } else {
                    Log.w(TAG, "Error al enviar el correo de confirmacion en RegisterDataActivity.");
                }
            }
        });
    }

    private Map<String, Object> recogerDatosTv() {
        Map<String, Object> mapDatosUsuario = new HashMap<>();
        Map<String, Object> mapaRms = new HashMap<>();
        mapDatosUsuario.put("Usuario", txtUsuario.getText().toString().trim());
        mapDatosUsuario.put("FechaNac", txtFecha.getText().toString().trim());
        mapDatosUsuario.put("Peso", txtPeso.getText() != null ? txtPeso.getText().toString().trim() : "");
        mapDatosUsuario.put("Altura", txtAltura.getText() != null ? txtAltura.getText().toString().trim() : "");
        if (!spinnerDeporte.getSelectedItem().toString().equals("Selecciona tu deporte")) {
            mapDatosUsuario.put("Deporte", spinnerDeporte.getSelectedItem().toString());
            if (txtRm1.getText() != null || txtRm2.getText() != null || txtRm3.getText() != null) {
                mapaRms.put(listaRms.get(0), txtRm1.getText().toString().trim() + " " + listaRms.get(3));
                mapaRms.put(listaRms.get(1), txtRm2.getText().toString().trim() + " " + listaRms.get(3));
                mapaRms.put(listaRms.get(2), txtRm3.getText().toString().trim() + " " + listaRms.get(3));
                mapDatosUsuario.put("MapaRms", mapaRms);
            } else {
                mapDatosUsuario.put("Deporte", "");
                mapDatosUsuario.put("MapaRms", mapaRms);
            }
        }
        return mapDatosUsuario;
    }

    private void inicializarTxt() {
        txtUsuario = findViewById(R.id.txtNombreUsuarioRegisterData);
        txtFecha = findViewById(R.id.txtFechaNacRegisterData);
        txtPeso = findViewById(R.id.txtPesoRegisterData);
        txtAltura = findViewById(R.id.txtAlturaRegisterData);
        txtRm1 = findViewById(R.id.txtRm1RegisterData);
        txtRm2 = findViewById(R.id.txtRm2RegisterData);
        txtRm3 = findViewById(R.id.txtRm3RegisterData);
        tilRm1 = findViewById(R.id.tilRm1RegisterData);
        tilRm2 = findViewById(R.id.tilRm2RegisterData);
        tilRm3 = findViewById(R.id.tilRm3RegisterData);
    }

    private void inicializarSpinner() {
        layoutRm = findViewById(R.id.layoutRM);
        spinnerDeporte = findViewById(R.id.spinnerDeporteRegisterData);
        spinnerDeporte.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerDeporte.requestFocus();
        switch (spinnerDeporte.getSelectedItem().toString()) {
            case "Calistenia":
                listaRms.add("Dominadas");
                listaRms.add("Fondos");
                listaRms.add("Flexiones");
                listaRms.add("repes");
                setRm("Dominadas", "Fondos", "Flexiones", "repes");
                layoutRm.setVisibility(View.VISIBLE);
                break;
            case "Crossfit":
                Toast.makeText(RegisterDataActivity.this, "Para eso vete al zoo", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finishAffinity();
                break;
            case "Culturismo":
                layoutRm.setVisibility(View.GONE);
                break;
            case "Powerlifting":
                listaRms.add("Press banca");
                listaRms.add("Peso muerto");
                listaRms.add("Sentadilla");
                listaRms.add("kg");
                setRm("Press banca", "Peso muerto", "Sentadilla", "kg");
                layoutRm.setVisibility(View.VISIBLE);
                break;
            case "Streetlifting":
                listaRms.add("Dominadas");
                listaRms.add("Fondos");
                listaRms.add("Sentadilla");
                listaRms.add("kg");
                setRm("Dominadas", "Fondos", "Sentadilla", "kg");
                layoutRm.setVisibility(View.VISIBLE);
                break;
            default:
                layoutRm.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        layoutRm.setVisibility(View.GONE);
    }

    private void showDatePickerDialog() {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Fecha nacimiento").build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> txtFecha.setText("" + materialDatePicker.getHeaderText()));

        materialDatePicker.addOnDismissListener(dialogInterface -> txtFecha.clearFocus());

        materialDatePicker.show(getSupportFragmentManager(), "TAG");
    }
}