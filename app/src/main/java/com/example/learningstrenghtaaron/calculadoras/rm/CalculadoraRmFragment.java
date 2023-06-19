package com.example.learningstrenghtaaron.calculadoras.rm;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
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
import android.widget.Spinner;

import com.example.learningstrenghtaaron.PantallaPrincipal;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.ajustes.SettingsFragment;
import com.example.learningstrenghtaaron.calculadoras.CalculadorasFragment;
import com.example.learningstrenghtaaron.calculadoras.macros.CalculadoraMacrosActivity;
import com.example.learningstrenghtaaron.calculadoras.macros.CalculadoraMacrosFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Map;

public class CalculadoraRmFragment extends Fragment {
    private Spinner spinnerCalculadoras;
    private TextInputLayout tilPeso, tilRepes;
    private TextInputEditText peso, repes;
    private MaterialButton calcular;
    private MaterialTextView brzycki, epley;
    private String speso, srepes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora_rm, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SettingsFragment.mPrefsName, MODE_PRIVATE);
        if (sharedPreferences.getBoolean("switchModoOscuro", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        inicializarComponentes(view);
        listeners(view);
        String[] datosUsuario = getActivity().getIntent().getStringArrayExtra("DatosUsuario");
        if (datosUsuario != null){
            peso.setText(datosUsuario[0]);
            repes.setText(datosUsuario[1]);
            calcular.callOnClick();
        }

        return view;
    }

    private void listeners(View view) {
        spinnerCalculadoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
/*                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frameLayoutPantallaPrincipal, new CalculadoraMacrosFragment()).commit();*/
                    PantallaPrincipal.viewPager.setCurrentItem(0);
                    spinnerCalculadoras.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                // TODO: metodo hideKeyboard
                //hideKeyboard(view);
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
                mostrarDialog(R.drawable.brzycki);
            }
        });
        epley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialog(R.drawable.epley);
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

    private void inicializarComponentes(View view) {
        spinnerCalculadoras = view.findViewById(R.id.spinnerCalculadorasCalculadoraRm);
        tilPeso = view.findViewById(R.id.tilPeso);
        tilRepes = view.findViewById(R.id.tilRepes);
        peso = view.findViewById(R.id.txtPesoCalculadoraRm);
        repes = view.findViewById(R.id.txtRpsCalculadoraRm);
        calcular = view.findViewById(R.id.btnCalcularCalculadoraRm);
        brzycki = view.findViewById(R.id.txtBrzyckiCalculadoraRm);
        epley = view.findViewById(R.id.txtEpleyCalculadoraRm);
    }

    private void mostrarDialog(int item){
        Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_calculadora_rm_formulas);
        ShapeableImageView formula = dialog.findViewById(R.id.imagenFormulas);
        formula.setImageResource(item);
        dialog.show();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}