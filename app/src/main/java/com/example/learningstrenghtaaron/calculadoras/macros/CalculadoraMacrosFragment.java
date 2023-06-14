package com.example.learningstrenghtaaron.calculadoras.macros;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.learningstrenghtaaron.PantallaPrincipal;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.ajustes.SettingsFragment;
import com.example.learningstrenghtaaron.calculadoras.CalculadorasFragment;
import com.example.learningstrenghtaaron.calculadoras.rm.CalculadoraRmFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Map;

public class CalculadoraMacrosFragment extends Fragment {
    TextInputLayout tilPeso, tilAltura, tilEdad;
    TextInputEditText peso, altura, edad;
    MaterialTextView volumen, definicion, mantenimiento, macrosVolumen, macrosDefinicion, macrosMantenimiento;
    RadioGroup radioGroup;
    RadioButton rbHombre, rbMujer;
    Spinner spinnerCalculadoras, spinnerActividad, spinnerObjetivo;
    MaterialButton btnInfo, btnCalcular;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora_macros, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SettingsFragment.mPrefsName, MODE_PRIVATE);
        if (sharedPreferences.getBoolean("switchModoOscuro", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        inicializarComponentes(view);
        listeners(view);

        return view;
    }

    private void listeners(View view) {
        spinnerCalculadoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
/*                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frameLayoutPantallaPrincipal, new CalculadoraRmFragment()).commit();*/
                    PantallaPrincipal.viewPager.setCurrentItem(1);
                    spinnerCalculadoras.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InfoActivity.class));
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: metodo hideKeyboard
                //hideKeyboard(view);

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

    private void inicializarComponentes(View view) {
        spinnerCalculadoras = view.findViewById(R.id.spinnerCalculadorasCalculadoraMacros);

        btnInfo = view.findViewById(R.id.btnInfoCalculadoraCalorias);
        tilPeso = view.findViewById(R.id.tilPesoCalculadoraMacros);
        peso = view.findViewById(R.id.txtPesoCalculadoraMacros);
        tilAltura = view.findViewById(R.id.tilAlturaCalculadoraMacros);
        altura = view.findViewById(R.id.txtAlturaCalculadoraMacros);
        tilEdad = view.findViewById(R.id.tilEdadCalculadoraMacros);
        edad = view.findViewById(R.id.txtEdadCalculadoraMacros);
        radioGroup = view.findViewById(R.id.radioGroupCalculadoraMacros);
        rbHombre = view.findViewById(R.id.rbtnHombreCalculadoraMacros);
        rbMujer = view.findViewById(R.id.rbtnMujerCalculadoraMacros);
        spinnerActividad = view.findViewById(R.id.spinnerActividadCalculadoraMacros);
        spinnerObjetivo = view.findViewById(R.id.spinnerObjetivoCalculadoraCalorias);
        btnCalcular = view.findViewById(R.id.btnCalcularCalculadoraMacros);

        volumen = view.findViewById(R.id.txtVolumenCalculadoraCalorias);
        definicion = view.findViewById(R.id.txtDefinicionCalculadoraCalorias);
        mantenimiento = view.findViewById(R.id.txtMantenimientoCalculadoraCalorias);
        macrosVolumen = view.findViewById(R.id.txtMacrosVolumenCalculadoraCalorias);
        macrosDefinicion = view.findViewById(R.id.txtMacrosDefinicionCalculadoraCalorias);
        macrosMantenimiento = view.findViewById(R.id.txtMacrosMantenimientoCalculadoraCalorias);
    }

    private void hideKeyboard(View view) {
        Intent intent = new Intent(getContext(), PantallaPrincipal.class);
        intent.putExtra("View", view.toString());
        startActivity(intent);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}