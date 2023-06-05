package com.example.learningstrenghtaaron.rutinas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learningstrenghtaaron.adaptadores.AdapterRutinas;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.RecyclerItemClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class RutinasFragment extends Fragment {
    private RecyclerView recyclerViewRutinas;
    private AdapterRutinas adapterRutinas;
    private FirebaseFirestore firestore;

    private ArrayList<Rutina> rutinas;

    public RutinasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutinas, container, false);
        //Relacionado Con RecyclerView
        recyclerViewRutinas = (RecyclerView) view.findViewById(R.id.RecyclerRutinas);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2); // 2 items por columna
        recyclerViewRutinas.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("Rutina");
        FirestoreRecyclerOptions<Rutina> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Rutina>().setQuery(query, Rutina.class).build();
        adapterRutinas = new AdapterRutinas(firestoreRecyclerOptions);
        adapterRutinas.notifyDataSetChanged();
        recyclerViewRutinas.setAdapter(adapterRutinas);
        rutinas = adapterRutinas.getRutinas();
        recyclerViewRutinas.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), recyclerViewRutinas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                abrirFragment(posicion);
            }

            @Override
            public void onLongItemClick(View v, int posicion) {

            }
        }));

        return view;
    }
    private void abrirFragment(int posicion) {
        Fragment nuevoFragment = new SemanasFragment();// Reemplaza "NuevoFragment" con el nombre de tu clase de Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("rutina",rutinas.get(posicion));
        nuevoFragment.setArguments(bundle);
        System.out.println(rutinas.get(posicion));
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.FragmentRutina,nuevoFragment).commit();
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterRutinas.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRutinas.stopListening();
    }


}