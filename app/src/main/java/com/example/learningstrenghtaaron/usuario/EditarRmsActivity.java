package com.example.learningstrenghtaaron.usuario;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.PantallaPrincipal;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.RecyclerItemClickListener;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class EditarRmsActivity extends AppCompatActivity {
    private EditText etEjercicio, etCantidad, etSufijo;
    private Button btnCancelDialog, btnAcceptDialog;
    private FloatingActionButton btnAddRm;
    private RecyclerView recyclerViewRms;
    private AdapterRms adapterRms;
    private Firestore firestore;
    private static Map<String, String> mapaRms;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rms);
        firestore = Firestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Inicializamos la toolbar
        Toolbar toolbar = findViewById(R.id.toolbarEditarRms);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            usuario.setMapaRms(adapterRms.getMapaRms());
            firestore.actualizarUsuario(usuario);
            Intent intent = new Intent(this, PantallaPrincipal.class);
            intent.putExtra("EDITAR", true);
            intent.putExtra("Usuario", usuario);
            startActivity(intent);
            return true;
        });
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        //Inicializamos el recyclerView
        recyclerViewRms = findViewById(R.id.rvEditarRms);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerViewRms.setLayoutManager(layoutManager);
        //Inicializamos el adaptador
        usuario = firestore.getUsuario(user.getUid());
        mapaRms = usuario.getMapaRms();
        adapterRms = new AdapterRms(mapaRms);
        adapterRms.notifyDataSetChanged();
        recyclerViewRms.setAdapter(adapterRms);
        //Inicializamos los componentes
        btnAddRm = findViewById(R.id.btnAddRmEditarRms);
        //Listener
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
        btnAddRm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditarRmsActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_add_rm);
                dialog.show();
                //Inicializamos los componentes del dialog
                etEjercicio = dialog.findViewById(R.id.etEjercicioDialogAddRm);
                etCantidad = dialog.findViewById(R.id.etCantidadDialogAddRm);
                etSufijo = dialog.findViewById(R.id.etSufijoDialogAddRm);
                btnCancelDialog = dialog.findViewById(R.id.btnCancelarDialogAddRm);
                btnAcceptDialog = dialog.findViewById(R.id.btnAceptarDialogAddRm);
                //Listeners de los botones
                btnCancelDialog.setOnClickListener(view1 -> dialog.dismiss());
                btnAcceptDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etEjercicio.getText().toString().isBlank()) etEjercicio.setError("Nose");
                        else if (etCantidad.getText().toString().isBlank()) etCantidad.setError("Nose");
                        else if (etSufijo.getText().toString().isBlank()) etSufijo.setError("Nose");
                        else mapaRms.put(etEjercicio.getText().toString().trim(), etCantidad.getText().toString().trim() + " " + etSufijo.getText().toString().trim());
                        adapterRms = new AdapterRms(mapaRms);
                        adapterRms.notifyDataSetChanged();
                        recyclerViewRms.setAdapter(adapterRms);
                        Toast.makeText(EditarRmsActivity.this, "Nuevo Rm insertado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_rms_menu,menu);
        return true;
    }
}