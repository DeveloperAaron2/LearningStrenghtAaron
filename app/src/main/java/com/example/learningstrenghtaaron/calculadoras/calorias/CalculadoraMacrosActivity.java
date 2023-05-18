package com.example.learningstrenghtaaron.calculadoras.calorias;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningstrenghtaaron.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Map;

public class CalculadoraMacrosActivity extends AppCompatActivity {

    TextInputLayout tilPeso, tilAltura, tilEdad;
    TextInputEditText peso, altura, edad;
    MaterialTextView volumen, definicion, mantenimiento, macrosVolumen, macrosDefinicion, macrosMantenimiento;
    RadioGroup radioGroup;
    RadioButton rbHombre, rbMujer;
    Spinner spinnerActividad, spinnerObjetivo;
    MaterialButton btnInfo, btnCalcular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_calorias);

        inicializarComponentes();

        listeners();

    }

    private void listeners() {
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculadoraMacrosActivity.this, InfoActivity.class));
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

                resetearCalorias();

                String sPeso = peso.getText().toString().trim();
                String sAltura = altura.getText().toString().trim();
                String sEdad = edad.getText().toString().trim();
                String sexo = "";
                if (rbHombre.isChecked()) sexo = "H"; else if (rbMujer.isChecked()) sexo = "M";
                String sActividad = spinnerActividad.getSelectedItem().toString();
                String objetivo = spinnerObjetivo.getSelectedItem().toString();

                if (!sPeso.isEmpty() && !sAltura.isEmpty() && !sEdad.isEmpty() && !sexo.isEmpty() && spinnerActividad.getSelectedItemPosition() >= 1) {
                    CalculadoraMacros calCal = new CalculadoraMacros(Integer.parseInt(sPeso), Integer.parseInt(sAltura), Integer.parseInt(sEdad)
                    , sexo, Double.parseDouble(sActividad.split("->")[0]), objetivo);
                    Map<String, Macros> mapMacros = calCal.getMacros();

                    mostrarMacros(mapMacros);
                }

            }
        });

        peso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tilPeso.setHint("");
                } else {
                    if (peso.getText().toString().isEmpty()) {
                        tilPeso.setHint("Peso");
                    }
                }
            }
        });

        altura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tilAltura.setHint("");
                } else {
                    if (altura.getText().toString().isEmpty()) {
                        tilAltura.setHint("Altura");
                    }
                }
            }
        });

        edad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tilEdad.setHint("");
                } else {
                    if (edad.getText().toString().isEmpty()) {
                        tilEdad.setHint("Edad");
                    }
                }
            }
        });
    }

    private void resetearCalorias() {
        volumen.setVisibility(View.GONE);
        macrosVolumen.setVisibility(View.GONE);
        definicion.setVisibility(View.GONE);
        macrosDefinicion.setVisibility(View.GONE);
        mantenimiento.setVisibility(View.GONE);
        macrosMantenimiento.setVisibility(View.GONE);
    }

    private void mostrarMacros(Map<String, Macros> mapMacros) {
        String objetivo = spinnerObjetivo.getSelectedItem().toString();
        if (objetivo.contains("Perder peso")){
            definicion.setVisibility(View.VISIBLE);
            definicion.setText(objetivo);
            macrosDefinicion.setVisibility(View.VISIBLE);
            macrosDefinicion.setText(mapMacros.get("Definicion").toString());

        } else if (objetivo.contains("Ganar peso")) {
            volumen.setVisibility(View.VISIBLE);
            volumen.setText(objetivo);
            macrosVolumen.setVisibility(View.VISIBLE);
            macrosVolumen.setText(mapMacros.get("Volumen").toString());

        } else if (objetivo.equals("Mantener peso")) {
            mantenimiento.setVisibility(View.VISIBLE);
            mantenimiento.setText(objetivo);
            macrosMantenimiento.setVisibility(View.VISIBLE);
            macrosMantenimiento.setText(mapMacros.get("Mantenimiento").toString());

        } else {
            volumen.setVisibility(View.VISIBLE);
            volumen.setText("Volumen");
            definicion.setVisibility(View.VISIBLE);
            definicion.setText("Definicion");
            mantenimiento.setVisibility(View.VISIBLE);
            mantenimiento.setText("Mantenimiento");
            macrosVolumen.setVisibility(View.VISIBLE);
            macrosVolumen.setText(mapMacros.get("Volumen").toString());
            macrosDefinicion.setVisibility(View.VISIBLE);
            macrosDefinicion.setText(mapMacros.get("Definicion").toString());
            macrosMantenimiento.setVisibility(View.VISIBLE);
            macrosMantenimiento.setText(mapMacros.get("Mantenimiento").toString());
        }
    }

    private void inicializarComponentes() {
        btnInfo = findViewById(R.id.btnInfoCalculadoraCalorias);
        tilPeso = findViewById(R.id.tilPesoCalculadoraMacros);
        peso = findViewById(R.id.txtPesoCalculadoraMacros);
        tilAltura = findViewById(R.id.tilAlturaCalculadoraMacros);
        altura = findViewById(R.id.txtAlturaCalculadoraMacros);
        tilEdad = findViewById(R.id.tilEdadCalculadoraMacros);
        edad = findViewById(R.id.txtEdadCalculadoraMacros);
        radioGroup = findViewById(R.id.radioGroupCalculadoraMacros);
        rbHombre = findViewById(R.id.rbtnHombreCalculadoraMacros);
        rbMujer = findViewById(R.id.rbtnMujerCalculadoraMacros);
        spinnerActividad = findViewById(R.id.spinnerActividadCalculadoraMacros);
        spinnerObjetivo = findViewById(R.id.spinnerObjetivoCalculadoraCalorias);
        btnCalcular = findViewById(R.id.btnCalcularCalculadoraMacros);

        volumen = findViewById(R.id.txtVolumenCalculadoraCalorias);
        definicion = findViewById(R.id.txtDefinicionCalculadoraCalorias);
        mantenimiento = findViewById(R.id.txtMantenimientoCalculadoraCalorias);
        macrosVolumen = findViewById(R.id.txtMacrosVolumenCalculadoraCalorias);
        macrosDefinicion = findViewById(R.id.txtMacrosDefinicionCalculadoraCalorias);
        macrosMantenimiento = findViewById(R.id.txtMacrosMantenimientoCalculadoraCalorias);
    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
