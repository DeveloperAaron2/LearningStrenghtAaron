package com.example.learningstrenghtaaron.rutinas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.Adaptadores.AdapterSemanasDias;
import com.example.learningstrenghtaaron.Entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SemanasDiasFragment extends Fragment {
    private RecyclerView recyclerViewSemanas;

    private FirebaseFirestore firestore;
    private long numeroElementos;
    private Rutina rutina;
    private AdapterSemanasDias adapterSemanas;
    private long NumeroSemana;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_semanas, container, false);
        recyclerViewSemanas = view.findViewById(R.id.RecyclerSemanas);
        recyclerViewSemanas.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Bundle args = getArguments();

        if (args != null) {
            rutina = (Rutina) args.getSerializable("rutina");
            String tipoFragment = (String) args.getString("TipoFragemnt");
            firestore = FirebaseFirestore.getInstance();
            if (tipoFragment.equals("Semanas")){// Obtén el valor del argumento
                CreaFragmentSemanas(container, rutina);
            }else if(tipoFragment.equals("Dias")){
                String semana = args.getString("Semana");
                CreaFragmentDias(container,semana,rutina);
            }
        }else{
            System.out.println("Se ha enviado mal el Bundle bro");
        }

        return view;

    }

    private void CreaFragmentDias(ViewGroup container,String semana, Rutina rutina) {
        RecogeNumeroDias(rutina,semana);

        recyclerViewSemanas.addOnItemTouchListener(new RecyclerItemClickListener(container.getContext(), recyclerViewSemanas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                abrirFragmentEjercicios(semana,posicion);
            }



            @Override
            public void onLongItemClick(View v, int posicion) {

            }
        }));
    }



    private void CreaFragmentSemanas(ViewGroup container, Rutina rutina) {
        RecogeNumeroSemanas(rutina);

        recyclerViewSemanas.addOnItemTouchListener(new RecyclerItemClickListener(container.getContext(), recyclerViewSemanas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                abrirFragment(posicion);
            }

            @Override
            public void onLongItemClick(View v, int posicion) {

            }
        }));
    }
    public void RecogeNumeroSemanas(Rutina rutina){
        numeroElementos = 0;
        CollectionReference ejercicioRutina = firestore.collection("EjercicioRutina");
        ejercicioRutina.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ejercicioRutina : queryDocumentSnapshots){
                    if(ejercicioRutina.getString("NombreRutina").equals(rutina.getNombreRutina())){
                        System.out.println(ejercicioRutina.getLong("NumeroSemana"));
                        if (ejercicioRutina.getLong("NumeroSemana")> numeroElementos){
                            numeroElementos = ejercicioRutina.getLong("NumeroSemana");
                        }
                    }
                }
                adapterSemanas= new AdapterSemanasDias((int) numeroElementos,"Semanas");
                recyclerViewSemanas.setAdapter(adapterSemanas);
                adapterSemanas.notifyDataSetChanged();
            }
        });
    }

    private void RecogeNumeroDias(Rutina rutina,String semana) {

        numeroElementos = 0;
        CollectionReference ejercicioRutina = firestore.collection("EjercicioRutina");
        ejercicioRutina.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ejercicioRutina : queryDocumentSnapshots){
                    if(ejercicioRutina.getString("NombreRutina").equals(rutina.getNombreRutina())){
                        String semanaRecogida = "Semana " + ejercicioRutina.getLong("NumeroSemana");
                        if (semanaRecogida.equals(semana)){
                            if(ejercicioRutina.getLong("NumeroDia")>numeroElementos)
                                numeroElementos = ejercicioRutina.getLong("NumeroDia");
                        }
                    }
                }
                System.out.println("Dias: " + numeroElementos);
                adapterSemanas= new AdapterSemanasDias((int) numeroElementos,"Dias");
                recyclerViewSemanas.setAdapter(adapterSemanas);
                adapterSemanas.notifyDataSetChanged();
            }
        });
    }


    private void abrirFragment(int posicion) {
        Fragment nuevoFragment = new SemanasDiasFragment();// Reemplaza "NuevoFragment" con el nombre de tu clase de Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("rutina",rutina);
        bundle.putString("Semana",adapterSemanas.getElementos().get(posicion));
        bundle.putString("TipoFragemnt","Dias");
        nuevoFragment.setArguments(bundle);
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.add(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
        fm.addToBackStack(null);
        fm.commit();
    }
    private void abrirFragmentEjercicios(String semana,int posicion) {
        Fragment nuevoFragment = new EjerciciosFragment();// Reemplaza "NuevoFragment" con el nombre de tu clase de Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("rutina",rutina);
        bundle.putString("Semana",semana);
        bundle.putString("Dia",adapterSemanas.getElementos().get(posicion));
        nuevoFragment.setArguments(bundle);
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
        fm.addToBackStack(null);
        fm.commit();
    }



}
