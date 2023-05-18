package com.example.learningstrenghtaaron.calculadoras.rm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningstrenghtaaron.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Map;

public class CalculadoraRmActivity extends AppCompatActivity {
    private TextInputLayout tilPeso, tilRepes;
    private TextInputEditText peso, repes;
    private MaterialButton calcular;
    private MaterialTextView brzycki, epley;
    private String speso, srepes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_rm);

        inicializarComponentes();

        listeners();

        String[] datosUsuario = getIntent().getStringArrayExtra("DatosUsuario");
        if (datosUsuario != null){
            peso.setText(datosUsuario[0]);
            repes.setText(datosUsuario[1]);
            calcular.callOnClick();
        }

    }

    private void listeners() {
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

        repes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tilRepes.setHint("");
                } else {
                    if (repes.getText().toString().isEmpty()) {
                        tilRepes.setHint("Repes");
                    }
                }
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                speso = peso.getText().toString().trim();
                srepes = repes.getText().toString().trim();

                if (!speso.isEmpty() && !srepes.isEmpty()) {
                    mostrarRms(speso, srepes);
                }
            }
        });
        brzycki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculadoraRmActivity.this, FormulasActivity.class);
                intent.putExtra("Formula", R.drawable.brzycki);
                intent.putExtra("DatosUsuario", new String[]{speso, srepes});
                startActivity(intent);
            }
        });
        epley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculadoraRmActivity.this, FormulasActivity.class);
                intent.putExtra("Formula", R.drawable.epley);
                intent.putExtra("DatosUsuario", new String[]{speso, srepes});
                startActivity(intent);
            }
        });
    }

    private void mostrarRms(String speso, String srepes) {
        CalculadoraRm rm = new CalculadoraRm(Integer.parseInt(speso), Integer.parseInt(srepes));
        Map<String, Integer> mapRm = rm.getRm();
        //Map<String, Integer> mapRm = new CalculadoraRm(Integer.parseInt(speso), Integer.parseInt(srepes)).getRm();
        brzycki.setText("Brzycki    -->     Tu Rm son " + mapRm.get("Brzycki") + " kgs.");
        epley.setText("Epley    -->     Tu Rm son " + mapRm.get("Epley") + " kgs.");
    }

    private void inicializarComponentes() {
        tilPeso = findViewById(R.id.tilPeso);
        tilRepes = findViewById(R.id.tilRepes);
        peso = findViewById(R.id.txtPesoCalculadoraRm);
        repes = findViewById(R.id.txtRpsCalculadoraRm);
        calcular = findViewById(R.id.btnCalcularCalculadoraRm);
        brzycki = findViewById(R.id.txtBrzyckiCalculadoraRm);
        epley = findViewById(R.id.txtEpleyCalculadoraRm);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
