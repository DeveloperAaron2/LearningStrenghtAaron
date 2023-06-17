package com.example.learningstrenghtaaron.anhadir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.adaptadores.AdapterAnhadirSemana;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class anhadir_semanas_fragment extends Fragment {
    private RecyclerView recyclerViewAnhadirSemanas;
    private AdapterAnhadirSemana adapterAnhadirSemana;

    private FloatingActionButton btnAnhadirSemana;

    private Button btnabrirTerminar;

    private HashMap<String,ArrayList<EjercicioRutina>> todosLosEjercicios;
    private ArrayList semanasAnhadidas;

    private Firestore firestore;


    public anhadir_semanas_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anhadir_semanas_fragment, container, false);
        recyclerViewAnhadirSemanas = v.findViewById(R.id.recyclerViewAnhadirSemanas);
        recyclerViewAnhadirSemanas.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapterAnhadirSemana = new AdapterAnhadirSemana(this);
        recyclerViewAnhadirSemanas.setAdapter(adapterAnhadirSemana);
        adapterAnhadirSemana.notifyDataSetChanged();
        firestore = Firestore.getInstance();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callbackmethod);
        itemTouchHelper.attachToRecyclerView(recyclerViewAnhadirSemanas);
        btnAnhadirSemana = (FloatingActionButton) v.findViewById(R.id.btnAnhadirSemana);
        btnabrirTerminar = (Button) v.findViewById(R.id.buttonAbrirTerminar);
        todosLosEjercicios = new HashMap<>();
        btnAnhadirSemana.setOnClickListener(v1 -> {
            ArrayList<String> semanas = adapterAnhadirSemana.getElementos();
            semanas.add("Semana " + (semanas.size() + 1));
            adapterAnhadirSemana.setElementos(semanas);
            adapterAnhadirSemana.notifyDataSetChanged();
        });
        btnabrirTerminar.setOnClickListener(v12 -> {
           Bundle bundleAntesDeTerminar= new Bundle();
           bundleAntesDeTerminar.putSerializable("SemanasAnhadidasFinal",semanasAnhadidas);
           bundleAntesDeTerminar.putSerializable("HasMapFinal",todosLosEjercicios);
           Fragment Terminar = new Terminar_Anhadir();
           Terminar.setArguments(bundleAntesDeTerminar);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fm = fragmentManager.beginTransaction();
            fm.replace(R.id.frameLayoutPantallaPrincipal, Terminar);
            fm.addToBackStack(null);
            fm.commit();
        });
        requireActivity().getSupportFragmentManager().setFragmentResultListener("TodosAñadidos", this, (requestKey, result) -> {

            semanasAnhadidas = (ArrayList) result.getSerializable("Semanas");
            adapterAnhadirSemana.setElementos(semanasAnhadidas);
            todosLosEjercicios = (HashMap<String, ArrayList<EjercicioRutina>>) result.getSerializable("HasMapLleno");
            adapterAnhadirSemana.notifyDataSetChanged();
        });
        requireActivity().getSupportFragmentManager().setFragmentResultListener("Fin", this, (requestKey, result) -> {
            semanasAnhadidas = (ArrayList) result.getSerializable("Semanas");
            adapterAnhadirSemana.setElementos(semanasAnhadidas);
            Rutina rutina = (Rutina) result.getSerializable("RutinaAnhadida");
            todosLosEjercicios = (HashMap<String, ArrayList<EjercicioRutina>>) result.getSerializable("HasMapFinal");
            adapterAnhadirSemana.notifyDataSetChanged();
            for(ArrayList<EjercicioRutina> ejercicioRutinas : todosLosEjercicios.values()){
                for (EjercicioRutina ejercicioRutina : ejercicioRutinas) {
                    ejercicioRutina.setNombreRutina(rutina.getNombreRutina());
                }
                firestore.InsertarEjerciciosRutina(ejercicioRutinas);
            }
            firestore.InsertarRutina(rutina);
            requireActivity().getSupportFragmentManager().setFragmentResult("FinAnhadir",null);
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack();
        });

        return v;
    }
    public void CambiarFragment(int position,String nombreSemana){
        Bundle bundle = new Bundle();
        bundle.putString("nombreDia","Dia " + position);
        System.out.println(bundle.get("nombreDia"));
        bundle.putString("nombreSemana",nombreSemana);
        bundle.putSerializable("Semanas",adapterAnhadirSemana.getElementos());
        bundle.putSerializable("EstructuraDeDatos",todosLosEjercicios);
        Fragment nuevoFragment = new anhadir_ejercicios_fragment();
        nuevoFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fm = fragmentManager.beginTransaction();
        fm.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
        fm.addToBackStack(null);
        fm.commit();
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
                    ArrayList<String> Semanas = adapterAnhadirSemana.getElementos();
                    Semanas.remove(itemPosition);
                    adapterAnhadirSemana.setElementos(Semanas);
                    adapterAnhadirSemana.notifyItemRemoved(itemPosition);
                    break;
                case ItemTouchHelper.RIGHT:
                    ArrayList<String> semanas = adapterAnhadirSemana.getElementos();
                    adapterAnhadirSemana.setElementos(semanas);
                    adapterAnhadirSemana.notifyDataSetChanged();
                    break;
            }
        }
    };
}