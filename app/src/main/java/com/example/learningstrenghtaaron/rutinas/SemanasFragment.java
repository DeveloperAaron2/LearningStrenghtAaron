package com.example.learningstrenghtaaron.rutinas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.adaptadores.AdapterSemanas;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SemanasFragment extends Fragment {
    private RecyclerView recyclerViewSemanas;

    private FirebaseFirestore firestore;
    private long numeroSemanas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_semanas, container, false);
        recyclerViewSemanas = view.findViewById(R.id.RecyclerSemanas);
        recyclerViewSemanas.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Bundle args = getArguments();

        if (args != null) {
            Rutina rutina = (Rutina) args.getSerializable("rutina"); // Obtén el valor del argumento
            RecogeNumeroSemanas(rutina, container.getContext());
        } else {
            System.out.println("Se ha enviado mal el Bundle bro");
        }

        return view;

    }

    public void RecogeNumeroSemanas(Rutina rutina, Context context) {
        firestore = FirebaseFirestore.getInstance();
        numeroSemanas = 0;
        CollectionReference ejercicioRutina = firestore.collection("EjercicioRutina");
        ejercicioRutina.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ejercicioRutina : queryDocumentSnapshots) {
                    if (ejercicioRutina.getString("NombreRutina").equals(rutina.getNombreRutina())) {
                        if (ejercicioRutina.getLong("NumeroSemana") > numeroSemanas) {
                            numeroSemanas = ejercicioRutina.getLong("NumeroSemana");
                        }
                    }
                }
                AdapterSemanas adapterSemanas = new AdapterSemanas((int) numeroSemanas);
                recyclerViewSemanas.setAdapter(adapterSemanas);
                adapterSemanas.notifyDataSetChanged();
            }
        });
    }
}
