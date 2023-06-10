package com.example.learningstrenghtaaron;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vista_Anhadir_Ejercicio extends Fragment {
    private anhadir_ejercicios_fragment controller;
    private TextView textViewDiaAnhadir;
    private EditText editTextNombreEjercicio;
    private EditText editTextSeriesReps;

    private EditText editTextIntensidad;
    private Button SubmitButton;
    private LinearLayout linearLayoutSeriesReps;

    public Vista_Anhadir_Ejercicio(){

    }

    public Vista_Anhadir_Ejercicio(anhadir_ejercicios_fragment controller){
        this.controller = controller;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vista_anhadir_ejercicio_nuevo, container, false);
        this.textViewDiaAnhadir =(TextView) v.findViewById(R.id.textViewDiaAnhadir);
        this.editTextNombreEjercicio = (EditText) v.findViewById(R.id.editTextNombreEjercicio);
        this.editTextSeriesReps = (EditText) v.findViewById(R.id.editTextSeriesReps);
        this.SubmitButton = v.findViewById(R.id.Submit_Ejercicio);
        Bundle bundle = getArguments();
        this.textViewDiaAnhadir.setText(bundle.getString("nombreDia"));
        this.editTextIntensidad = v.findViewById(R.id.IntensidadText);
        this.linearLayoutSeriesReps = v.findViewById(R.id.LinearLayoutSeriesRepsIntensidad);
        this.SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ComprobarEditText()){
                    long numeroDia = Integer.parseInt(bundle.getString("nombreDia").split(" ")[1]);
                    long numeroSemana = Integer.parseInt(bundle.getString("nombreSemana").split(" ")[1]);
                    HashMap<String,Integer> seriesRespIntensidad = new HashMap<>();
                    seriesRespIntensidad.put(editTextSeriesReps.getText().toString(),Integer.parseInt(editTextIntensidad.getText().toString()));
                    EjercicioRutina ejercicioRutina = new EjercicioRutina(editTextNombreEjercicio.getText().toString(),
                            "SinAsignar",
                            (int)numeroDia,
                            (int)numeroSemana,
                            seriesRespIntensidad);
                    ArrayList<EjercicioRutina> ejercicioRutinas = controller.getEjercicioRutinas();
                    if(ejercicioRutinas.size()>0){
                        boolean existe = false;
                        for (EjercicioRutina rutina : ejercicioRutinas) {
                            if(rutina.getNombreEjercicio().equals(ejercicioRutina.getNombreEjercicio())){
                                rutina.getSeriesReps().put(editTextSeriesReps.getText().toString(),Integer.parseInt(editTextIntensidad.getText().toString()));
                                existe=true;
                            }
                        }
                        if(!existe)
                            ejercicioRutinas.add(ejercicioRutina);
                    }else{
                        ejercicioRutinas.add(ejercicioRutina);}

                    controller.setEjercicioRutinas(ejercicioRutinas);
                    FragmentManager fragmentManager = getParentFragmentManager(); // O getFragmentManager() si estás en una actividad
                    // Volver al fragmento anterior y eliminar el fragmento actual
                    fragmentManager.popBackStack();
                }
            }
        });
        return v;
    }

    private boolean ComprobarEditText() {
        boolean isNotEmpty = true;
        if (editTextNombreEjercicio.getText().equals(""))
            isNotEmpty=false;
        else if (editTextSeriesReps.getText().equals(""))
            isNotEmpty=false;
        return isNotEmpty;
    }
}
