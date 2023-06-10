package com.example.learningstrenghtaaron;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learningstrenghtaaron.Adaptadores.AdapterAnhadirSemana;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class anhadir_semanas_fragment extends Fragment {
    private RecyclerView recyclerViewAnhadirSemanas;
    private AdapterAnhadirSemana adapterAnhadirSemana;

    private FloatingActionButton btnAnhadirSemana;

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callbackmethod);
        itemTouchHelper.attachToRecyclerView(recyclerViewAnhadirSemanas);
        btnAnhadirSemana = (FloatingActionButton) v.findViewById(R.id.btnAnhadirSemana);

        btnAnhadirSemana.setOnClickListener(v1 -> {
            ArrayList<String> semanas = adapterAnhadirSemana.getElementos();
            semanas.add("Semana " + (semanas.size() + 1));
            adapterAnhadirSemana.setElementos(semanas);
            adapterAnhadirSemana.notifyDataSetChanged();
        });
        return v;
    }
    public void CambiarFragment(int position,String nombreSemana){
        Bundle bundle = new Bundle();
        bundle.putString("nombreDia","Dia " + position);
        System.out.println(bundle.get("nombreDia"));
        bundle.putString("nombreSemana",nombreSemana);
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