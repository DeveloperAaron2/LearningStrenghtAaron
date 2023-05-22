package com.example.learningstrenghtaaron.rutinas;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learningstrenghtaaron.Adaptadores.AdapterEjercicios;
import com.example.learningstrenghtaaron.Adaptadores.AdapterRutinas;
import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.Entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class EjerciciosFragment extends Fragment {

    private RecyclerView recyclerViewEjercicios;

    private FirebaseFirestore firestore;

    private ArrayList<EjercicioRutina> ejercicios;

    public EjerciciosFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicios, container, false);
        recyclerViewEjercicios = (RecyclerView) view.findViewById(R.id.RecyclerViewEjercicios);
        recyclerViewEjercicios.setLayoutManager(new LinearLayoutManager(view.getContext()));
        firestore = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        Rutina rutina = (Rutina) bundle.getSerializable("rutina");
        try {
            long semana = Integer.parseInt((String) bundle.getString("Semana").split(" ")[1]);
            long dia = Integer.parseInt((String) bundle.getString("Dia").split(" ")[1]);

            System.out.println("semana " + semana + "dia " + dia);
            //RecyclerView
            Query query = firestore.collection("EjercicioRutina").whereEqualTo("NombreRutina", rutina.getNombreRutina()).whereEqualTo("NumeroSemana", semana).whereEqualTo("NumeroDia", dia).limit(1000);
            FirestoreRecyclerOptions<EjercicioRutina> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<EjercicioRutina>().setQuery(query, EjercicioRutina.class).build();
            AdapterEjercicios adapterEjercicios = new AdapterEjercicios(firestoreRecyclerOptions);
            adapterEjercicios.notifyDataSetChanged();
            recyclerViewEjercicios.setAdapter(adapterEjercicios);
            System.out.println("adapter puesto, en teoría");
            ejercicios = adapterEjercicios.getEjercicios();

        } catch (NumberFormatException e) {
            Log.d("JODIDO","esta esta vacía");
        }
        return view;
    }
}