package com.example.learningstrenghtaaron.usuario;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.RecyclerItemClickListener;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class EditarRmsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRms;
    private AdapterRms adapterRms;
    private Firestore firestore;

    private Map<String, String> mapaRms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rms);
        firestore = Firestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Inicializamos el recyclerView
        recyclerViewRms = (RecyclerView) findViewById(R.id.rvEditarRms);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerViewRms.setLayoutManager(layoutManager);
        //Inicializamos el adaptador
        Usuario usuario = firestore.getUsuario(user.getUid());
        mapaRms = usuario.getMapaRms();
        adapterRms = new AdapterRms(mapaRms);
        adapterRms.notifyDataSetChanged();
        recyclerViewRms.setAdapter(adapterRms);
        //Listener
        //mapaRms = adapterRms.getRutinas();
        recyclerViewRms.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewRms, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                System.out.println("Click item: " + posicion);
            }

            @Override
            public void onLongItemClick(View v, int posicion) {
                System.out.println("LongClick item: " + posicion);
            }
        }));
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}