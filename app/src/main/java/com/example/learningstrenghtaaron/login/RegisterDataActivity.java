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

import com.example.learningstrenghtaaron.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class RegisterDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // TODO: cambiar edad por fecha de nacimiento
    private TextInputEditText tVUsuario, tVEdad, tVPeso, tVAltura, tVRm1, tVRm2, tVRm3;
    private Spinner spinnerDeporte;
    private LinearLayout layoutRm;
    private Button btnEntrar;
    private String usuario, edad, peso, altura, deporte, rm1, rm2, rm3;
    private Map<String, String> mapUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        inicializarEditText();

        inicializarSpinner();

        botonEntrar();
    }

    private void setRm(String rm1, String rm2, String rm3, String tipo) {
        tVRm1.setHint(tipo);
        tVRm2.setHint(tipo);
        tVRm3.setHint(tipo);

        tVRm1.setText(rm1);
        tVRm2.setText(rm2);
        tVRm3.setText(rm3);
    }

    private void botonEntrar() {
        btnEntrar = findViewById(R.id.btnEntrarRegisterData);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recogerDatosTv();
                if (usuario.isEmpty() || edad.isEmpty()) {
                    Toast.makeText(RegisterDataActivity.this, "Por favor, rellene los campos marcados con un *.", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Subir datos a la base de datos
                    subirABd();

                    enviarCorreoVerificacion();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(RegisterDataActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void subirABd() {
        inicializarMapa();
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

    private void inicializarMapa() {
        mapUsuario = new HashMap<>();
        mapUsuario.put("Usuario", usuario);
        mapUsuario.put("Edad", edad);
        mapUsuario.put("Peso", peso);
        mapUsuario.put("Altura", altura);
        // TODO: si el deporte es culturismo los rm seran nulos
        if (!deporte.equals("Selecciona tu deporte")) {
            mapUsuario.put("Deporte", deporte);
            mapUsuario.put("Rm1", rm1);
            mapUsuario.put("Rm2", rm2);
            mapUsuario.put("Rm3", rm3);
        }
    }

    private void recogerDatosTv() {
        usuario = tVUsuario.getText().toString().trim();
        edad = tVEdad.getText().toString().trim();
        peso = tVPeso.getText().toString().trim();
        altura = tVAltura.getText().toString().trim();
        deporte = spinnerDeporte.getSelectedItem().toString();
        rm1 = tVRm1.getText().toString().trim();
        rm2 = tVRm2.getText().toString().trim();
        rm3 = tVRm3.getText().toString().trim();
    }

    private void inicializarEditText() {
        tVUsuario = findViewById(R.id.txtNombreUsuarioRegisterData);
        tVEdad = findViewById(R.id.txtEdadRegisterData);
        tVPeso = findViewById(R.id.txtPesoRegisterData);
        tVAltura = findViewById(R.id.txtAlturaRegisterData);
        tVRm1 = findViewById(R.id.txtRm1RegisterData);
        tVRm2 = findViewById(R.id.txtRm2RegisterData);
        tVRm3 = findViewById(R.id.txtRm3RegisterData);
    }

    private void inicializarSpinner() {
        layoutRm = findViewById(R.id.layoutRM);
        spinnerDeporte = findViewById(R.id.spinnerDeporteRegisterData);
        spinnerDeporte.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (spinnerDeporte.getSelectedItem().toString()) {
            case "Calistenia":
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
                setRm("Press banca", "Peso muerto", "Sentadilla", "kg");
                layoutRm.setVisibility(View.VISIBLE);
                break;
            case "Streetlifting":
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
}