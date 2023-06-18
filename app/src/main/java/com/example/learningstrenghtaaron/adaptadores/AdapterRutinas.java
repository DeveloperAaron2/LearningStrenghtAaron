package com.example.learningstrenghtaaron.adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.rutinas.RutinasFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterRutinas extends FirestoreRecyclerAdapter<Rutina, AdapterRutinas.ViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ArrayList<Rutina> rutinas;
    private Firestore firestore;
    private RutinasFragment controller;

    private String tipo;

    private boolean esCreador;

    public AdapterRutinas(@NonNull FirestoreRecyclerOptions<Rutina> options, RutinasFragment controller,String tipo) {
        super(options);
        rutinas = new ArrayList<>();
        firestore = Firestore.getInstance();
        this.controller=controller;
        this.tipo = tipo;

    }

    /**
     * @param holder
     * @param position
     * @param model    the model object containing the data that should be used to populate the view.
     */
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Rutina model) {
        holder.nombreRutina.setText(model.getNombreRutina());
        holder.tipoRutina.setText(model.getTipoRutina());
        holder.creador.setText("Creador:  " + model.getCreador());
        if(firestore.getUsuario()!=null){
            System.out.println(firestore.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid()).getUsuario());
            if(firestore.getUsuario().getUsuario().equals(model.getCreador()))
            esCreador=true;
        }
        else
            esCreador=false;
        if(model.getTipoRutina().equals("Fuerza")) {
            holder.icono.setImageResource(R.drawable.rutinafuerzalogo);
        } else if (model.getTipoRutina().equals("Hipertrofia")) {
            holder.icono.setImageResource(R.drawable.hipertrofiaicono);
        }
        holder.btnMenuFragmentRutinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v,R.menu.fragment_rutinas_menu,model);
            }
        });
        holder.icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.abrirFragment(position);
            }
        });
        rutinas.add(model);

    }


    public void showMenu(View view, int fragment_rutinas_menu, Rutina model) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(fragment_rutinas_menu, popup.getMenu());
        if(tipo.equals("MisRutinas")){
            popup.getMenu().getItem(0).setVisible(false);
        }
        else{
            popup.getMenu().getItem(2).setVisible(false);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Agregar_Rutina:
                            TieneLaRutina(model,view,"Agregar");
                        break;
                    case R.id.BorrarRutina:
                        TieneLaRutina(model,view,"Borrar");
                        break;
                    case R.id.EliminarRutina:
                        System.out.println(firestore.getUsuario().getUsuario());
                        if(firestore.getUsuario().getUsuario().equals(model.getCreador()))
                            firestore.BorrarRutina(model.getNombreRutina());
                        else
                            Toast.makeText(view.getContext(), "No puedes borrar una rutina si no eres su creador", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        popup.setOnDismissListener(popupMenu -> {
        });
        popup.show();
    }

    private void TieneLaRutina(Rutina model, View view,String tipo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = firestore.getFirestore().collection("Rutina").document(model.getNombreRutina());
        Task<DocumentSnapshot> document = docRef.get();
        if(document!=null){
            document.addOnSuccessListener(documentSnapshot -> {
                ArrayList<Object> objetos = (ArrayList<Object>) documentSnapshot.get("Usuarios");
                if(user.isAnonymous()){
                    if(tipo.equals("Agregar"))
                        Toast.makeText(view.getContext(), "No puedes guardar rutinas si no estás registrado", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(), "No puedes desagregar rutinas si no estás registrado", Toast.LENGTH_SHORT).show();
                }
                else if (objetos==null && tipo.equals("Agregar")){
                    objetos = new ArrayList<>();
                    objetos.add(user.getUid());
                }
                else if (objetos.contains(user.getUid())) {
                    if(tipo.equals("Agregar"))
                    Toast.makeText(view.getContext(), "Ya tienes agregada esta rutina", Toast.LENGTH_SHORT).show();
                    else{
                        objetos.remove(user.getUid());
                        Toast.makeText(view.getContext(), "Rutina borrada de MisRutinas", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    objetos.add(user.getUid());
                    Toast.makeText(view.getContext(), "Rutina agregada a MisRutinas", Toast.LENGTH_SHORT).show();
                }
                HashMap<String, Object> datos = new HashMap<>();
                datos.put("Usuarios", objetos);
                docRef.update(datos);
            });
            }
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary  calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rutinasview, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreRutina;
        private TextView tipoRutina;
        private ImageView icono;
        private TextView creador;
        private FloatingActionButton btnMenuFragmentRutinas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRutina = (TextView) itemView.findViewById(R.id.nombreRutina);
            tipoRutina = (TextView) itemView.findViewById(R.id.TipoRutina);
            icono =(ImageView) itemView.findViewById(R.id.iconoRutina);
            creador = (TextView) itemView.findViewById(R.id.CreadorRutina);
            btnMenuFragmentRutinas = (FloatingActionButton) itemView.findViewById(R.id.btnMenuFragmentRutinas);

        }
    }

    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }

}
