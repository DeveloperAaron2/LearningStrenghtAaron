package com.example.learningstrenghtaaron.anhadir;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.adaptadores.AdapterAnhadirEjercicios;
import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.adaptadores.AdapterAnhadirEjercicios;
import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class anhadir_ejercicios_fragment extends Fragment {

    private RecyclerView recyclerViewAnhadirEjercicios;

    private FloatingActionButton btnanhadirEjercicio;
    private ArrayList<EjercicioRutina> ejercicioRutinas;
    private AdapterAnhadirEjercicios adaterAnhadirEjercicios;

    private anhadir_ejercicios_fragment controller;
    private Button Terminar;

    private String nombreDia;

    private String nombreSemana;

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
        Terminar = (Button) v.findViewById(R.id.buttonTerminarEjercicios);
        Bundle bundle = getArguments();
        nombreDia = bundle.getString("nombreDia").split(" ")[1];
        nombreSemana = bundle.getString("nombreSemana").split(" ")[1];
        HashMap<String,ArrayList<EjercicioRutina>> elementosEnviados = (HashMap<String, ArrayList<EjercicioRutina>>) bundle.getSerializable("EstructuraDeDatos");
        boolean estaYaCreado= false;
        if(elementosEnviados!=null){
            for (String elementoEnviado : elementosEnviados.keySet()){
                System.out.println(elementoEnviado);
                System.out.println(nombreSemana + " " + nombreDia);
                if(elementoEnviado.equals(nombreSemana + " " + nombreDia)){
                    estaYaCreado=true;
                    System.out.println("esto no sale pq está vacío pringao");
                    adaterAnhadirEjercicios = new AdapterAnhadirEjercicios(elementosEnviados.get(elementoEnviado));
                    for (EjercicioRutina ejercicioRutina : elementosEnviados.get(elementoEnviado)) {
                        System.out.println(ejercicioRutina);
                    }

                }
            }
        }
        if(!estaYaCreado)
            adaterAnhadirEjercicios = new AdapterAnhadirEjercicios();
        ejercicioRutinas= adaterAnhadirEjercicios.getElementos();
        recyclerViewAnhadirEjercicios.setAdapter(adaterAnhadirEjercicios);
        adaterAnhadirEjercicios.notifyDataSetChanged();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callbackmethod);
        itemTouchHelper.attachToRecyclerView(recyclerViewAnhadirEjercicios);
        controller = this;
        InicializaBotones(bundle);
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
    ItemTouchHelper.SimpleCallback callbackmethod = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int itemPosition = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    ArrayList<EjercicioRutina> Ejercicios = adaterAnhadirEjercicios.getElementos();
                    Ejercicios.remove(itemPosition);
                    adaterAnhadirEjercicios.setElementos(Ejercicios);
                    adaterAnhadirEjercicios.notifyItemRemoved(itemPosition);
                    break;
                case ItemTouchHelper.RIGHT:
                    ArrayList<EjercicioRutina> ejercicios = adaterAnhadirEjercicios.getElementos();
                    adaterAnhadirEjercicios.setElementos(ejercicios);
                    adaterAnhadirEjercicios.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void InicializaBotones(Bundle bundle) {
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
        Terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                ejercicioRutinas = adaterAnhadirEjercicios.getElementos();// O getFragmentManager() si estás en una actividad
                HashMap<String,ArrayList<EjercicioRutina>>todosLosEjercicios = (HashMap<String, ArrayList<EjercicioRutina>>) bundle.getSerializable("EstructuraDeDatos");
                todosLosEjercicios.put(nombreSemana + " " + nombreDia,ejercicioRutinas);
                bundle.putSerializable("HasMapLleno",todosLosEjercicios);
                requireActivity().getSupportFragmentManager().setFragmentResult("TodosAñadidos",bundle);
                fragmentManager.popBackStack();
            }
        });
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
