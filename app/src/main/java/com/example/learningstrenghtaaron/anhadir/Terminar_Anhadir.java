package com.example.learningstrenghtaaron.anhadir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;


public class Terminar_Anhadir extends Fragment {
    private EditText nombreRutina;
    private Switch tipoRutina;
    private Switch publica_privada;
    private Button terminaranhadir;

    String tipoRutinaterminar;
    String publicaPrivada;
    private ImageView iconoTerminar;
    public Terminar_Anhadir() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_terminar__anhadir, container, false);
        // Inflate the layout for this fragment
        nombreRutina = v.findViewById(R.id.editTextNombreRutinaTerminar);
        tipoRutina = v.findViewById(R.id.switchTipo);
        publica_privada = v.findViewById(R.id.SwitchPublicaPrivada);
        terminaranhadir = v.findViewById(R.id.buttonTerminarAnhadir);
        iconoTerminar = v.findViewById(R.id.iconoTerminar);
        tipoRutinaterminar= "Hipertrofia";
        Bundle bundle = new Bundle();
        tipoRutina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    iconoTerminar.setImageResource(R.drawable.rutinafuerzalogo);
                    tipoRutina.setText("Fuerza");
                    tipoRutinaterminar= "Fuerza";
                }
                else if (!isChecked){
                    iconoTerminar.setImageResource(R.drawable.hipertrofiaicono);
                    tipoRutina.setText("Hipertrofia");
                    tipoRutinaterminar= "Hipertrofia";
                }
            }
        });
        publica_privada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    publicaPrivada = "privada";
                } else if (!isChecked){
                    publicaPrivada="publica";
                }
            }
        });
        terminaranhadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("RutinaAnhadida",new Rutina());
                requireActivity().getSupportFragmentManager().setFragmentResult("Fin",bundle);
            }
        });
        return v;
    }
}