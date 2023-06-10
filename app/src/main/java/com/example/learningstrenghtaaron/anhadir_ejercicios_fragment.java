package com.example.learningstrenghtaaron;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
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
        Bundle bundle = getArguments();

        adaterAnhadirEjercicios = new AdaterAnhadirEjercicios();
        ejercicioRutinas= adaterAnhadirEjercicios.getElementos();
        recyclerViewAnhadirEjercicios.setAdapter(adaterAnhadirEjercicios);
        adaterAnhadirEjercicios.notifyDataSetChanged();
        controller = this;

        btnanhadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new Vista_Anhadir_Ejercicio(controller);
                nuevoFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                bundle.putSerializable("EjerciciosRutina", ejercicioRutinas);
                FragmentTransaction fm = fragmentManager.beginTransaction();
                fm.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
                fm.addToBackStack(null);
                fm.commit();
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("Añadido", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                ejercicioRutinas = (ArrayList<EjercicioRutina>) result.getSerializable("Ejercicios");
                adaterAnhadirEjercicios.setElementos(ejercicioRutinas);
                adaterAnhadirEjercicios.notifyDataSetChanged();
            }
        });
        return v;
    }

    public ArrayList<EjercicioRutina> getEjercicioRutinas() {
        return ejercicioRutinas;
    }

    public void setEjercicioRutinas(ArrayList<EjercicioRutina> ejercicioRutinas) {
        System.out.println(ejercicioRutinas.get(0));
        adaterAnhadirEjercicios.setElementos(ejercicioRutinas);
        adaterAnhadirEjercicios.notifyDataSetChanged();
    }

    /*public void setEjercicioRutinas(ArrayList<EjercicioRutina> ejercicioRutinas) {
        this.ejercicioRutinas = ejercicioRutinas;
        adaterAnhadirEjercicios.setElementos(ejercicioRutinas);
        adaterAnhadirEjercicios.notifyDataSetChanged();
        System.out.println(adaterAnhadirEjercicios.getItemCount());
    }*/




}
