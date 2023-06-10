package com.example.learningstrenghtaaron;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.Adaptadores.AdapterAnhadirSemana;
import com.example.learningstrenghtaaron.Adaptadores.AdaterAnhadirEjercicios;
import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class anhadir_ejercicios_fragment extends Fragment {

    private RecyclerView recyclerViewAnhadirEjercicios;

    private FloatingActionButton btnanhadirEjercicio;
    private ArrayList<EjercicioRutina> ejercicioRutinas;
    private AdaterAnhadirEjercicios adaterAnhadirEjercicios;

    private anhadir_ejercicios_fragment controller;
    public anhadir_ejercicios_fragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.anhadir_ejercicios, container, false);
        recyclerViewAnhadirEjercicios = (RecyclerView) v.findViewById(R.id.recyclerviewAnhadirEjercicios);
        btnanhadirEjercicio = (FloatingActionButton) v.findViewById(R.id.btnAnhadirEjercicio);
        recyclerViewAnhadirEjercicios.setLayoutManager(new LinearLayoutManager(v.getContext()));
        ArrayList<EjercicioRutina> ejercicioRutinas1 = new ArrayList<>();
        adaterAnhadirEjercicios = new AdaterAnhadirEjercicios(ejercicioRutinas1);
        recyclerViewAnhadirEjercicios.setAdapter(adaterAnhadirEjercicios);
        adaterAnhadirEjercicios.notifyDataSetChanged();
        controller = this;
        Bundle bundle = getArguments();
        ejercicioRutinas= adaterAnhadirEjercicios.getElementos();

        btnanhadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new Vista_Anhadir_Ejercicio(controller);
                nuevoFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fm = fragmentManager.beginTransaction();
                fm.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
                fm.addToBackStack(null);
                fm.commit();
            }
        });
        return v;
    }

    public ArrayList<EjercicioRutina> getEjercicioRutinas() {
        return ejercicioRutinas;
    }

    /*public void setEjercicioRutinas(ArrayList<EjercicioRutina> ejercicioRutinas) {
        this.ejercicioRutinas = ejercicioRutinas;
        adaterAnhadirEjercicios.setElementos(ejercicioRutinas);
        adaterAnhadirEjercicios.notifyDataSetChanged();
        System.out.println(adaterAnhadirEjercicios.getItemCount());
    }*/

    public AdaterAnhadirEjercicios getAdaterAnhadirEjercicios() {
        return adaterAnhadirEjercicios;
    }

    public void setAdaterAnhadirEjercicios(AdaterAnhadirEjercicios adaterAnhadirEjercicios) {
        this.adaterAnhadirEjercicios = adaterAnhadirEjercicios;
    }

    public void setEjercicioRutinas(ArrayList<EjercicioRutina> ejercicioRutinas) {
        adaterAnhadirEjercicios.setElementos(ejercicioRutinas);
        adaterAnhadirEjercicios.notifyDataSetChanged();
    }
}
