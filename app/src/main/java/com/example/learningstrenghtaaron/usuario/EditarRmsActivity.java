package com.example.learningstrenghtaaron.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.RecyclerItemClickListener;
import com.example.learningstrenghtaaron.adaptadores.AdapterRutinas;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Map;

public class EditarRmsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRms;
    private AdapterRutinas adapterRms;
    private FirebaseFirestore firestore;

    private Map<String, String> mapaRms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rms);

        recyclerViewRms = (RecyclerView) findViewById(R.id.rvEditarRms);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 items por columna
        recyclerViewRms.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("Rutina");
        FirestoreRecyclerOptions<Rutina> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Rutina>().setQuery(query, Rutina.class).build();
        adapterRms = new AdapterRutinas(firestoreRecyclerOptions);
        adapterRms.notifyDataSetChanged();
        recyclerViewRms.setAdapter(adapterRms);
        mapaRms = adapterRms.getRutinas();
        recyclerViewRms.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewRms, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {

            }

            @Override
            public void onLongItemClick(View v, int posicion) {

            }
        }));
    }
}