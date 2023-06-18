package com.example.learningstrenghtaaron.rutinas;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.adaptadores.AdapterRutinas;
import com.example.learningstrenghtaaron.anhadir.anhadir_semanas_fragment;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class RutinasFragment extends Fragment {
    private RecyclerView recyclerViewRutinas;
    private AdapterRutinas adapterRutinas;
    private FirebaseFirestore firestore;
    private Firestore firestorebbdd;

    private ArrayList<Rutina> rutinas;

    private  Query query;
    private Context context;

    private FloatingActionButton btnAñadirRutina;

    public RutinasFragment() {

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
        btnAñadirRutina = (FloatingActionButton) view.findViewById(R.id.btnAñadirRutinaFragmentRutinas);
        CreaRecyclerView(view);
        AñadirRutina();
        this.context = getContext();
        firestorebbdd = Firestore.getInstance();
        requireActivity().getSupportFragmentManager().setFragmentResultListener("FinAnhadir", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                adapterRutinas.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void AñadirRutina() {
        btnAñadirRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firestorebbdd.getUsuario().getUsuario()==null){
                    Toast.makeText(context,"No puedes crear Rutinas en anónimo",Toast.LENGTH_SHORT).show();
                }else{
                Fragment nuevoFragment = new anhadir_semanas_fragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
                fm.addToBackStack(null);
                fm.commit();}
            }
        });
    }

    private void CreaRecyclerView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2); // 2 items por columna
        recyclerViewRutinas.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        String tipo = bundle.getString("Tipo");
        if (tipo.equals("Rutinas")) {
             query = firestore.collection("Rutina").whereEqualTo("acceso", "pública").limit(1000);
        }else if (tipo.equals("MisRutinas")) {
            String nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
            query = firestore.collection("Rutina").whereArrayContains("Usuarios",nombreUsuario).limit(1000);
            btnAñadirRutina.setVisibility(View.INVISIBLE);
        }
        FirestoreRecyclerOptions<Rutina> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Rutina>().setQuery(query, Rutina.class).build();
        adapterRutinas = new AdapterRutinas(firestoreRecyclerOptions,this,tipo);
        adapterRutinas.notifyDataSetChanged();
        recyclerViewRutinas.setAdapter(adapterRutinas);
        rutinas = adapterRutinas.getRutinas();
        System.out.println("tamaño rutinas: "+ rutinas.size());
    }

   /* public void añadirListener(){
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
    }*/

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