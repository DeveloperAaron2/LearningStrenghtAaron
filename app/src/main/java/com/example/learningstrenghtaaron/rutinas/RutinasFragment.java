package com.example.learningstrenghtaaron.rutinas;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learningstrenghtaaron.Adaptadores.AdapterRutinas;
import com.example.learningstrenghtaaron.Entidades.Rutina;
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
    private MediaPlayer mp;
    private  Query query;

    public RutinasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("este va primero");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("este va segundo");
        View view = inflater.inflate(R.layout.fragment_rutinas, container, false);
        //Relacionado Con RecyclerView
        recyclerViewRutinas = (RecyclerView) view.findViewById(R.id.RecyclerRutinas);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2); // 2 items por columna
        recyclerViewRutinas.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        String tipo = bundle.getString("Tipo");
        if (tipo.equals("Rutinas")) {
             query = firestore.collection("Rutina").whereEqualTo("acceso", "pública").limit(1000);
        }else if (tipo.equals("MisRutinas")) {
            String nombreUsuario = (String) bundle.get("NombreUsuario");
            query = firestore.collection("Rutina").whereEqualTo("acceso","pública").whereArrayContains("Usuarios",nombreUsuario).limit(1000);
        }
        FirestoreRecyclerOptions<Rutina> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Rutina>().setQuery(query, Rutina.class).build();
        adapterRutinas = new AdapterRutinas(firestoreRecyclerOptions,this);
        adapterRutinas.notifyDataSetChanged();
        recyclerViewRutinas.setAdapter(adapterRutinas);
        rutinas = adapterRutinas.getRutinas();
        System.out.println("tamaño rutinas: "+ rutinas.size());
        mp= MediaPlayer.create(requireContext(), R.raw.kyriakosgrizzly);
        return view;
    }
    public void añadirListener(){
        recyclerViewRutinas.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerViewRutinas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                mp.start();
                abrirFragment(posicion);
            }
            @Override
            public void onLongItemClick(View v, int posicion) {

            }
        }));
    }
    public void abrirFragment(int posicion) {
        Fragment nuevoFragment = new SemanasDiasFragment();// Reemplaza "NuevoFragment" con el nombre de tu clase de Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("rutina",rutinas.get(posicion));
        bundle.putString("TipoFragemnt","Semanas");
        nuevoFragment.setArguments(bundle);
        System.out.println(rutinas.get(posicion));
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.add(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
        fm.addToBackStack(null);
        fm.commit();
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