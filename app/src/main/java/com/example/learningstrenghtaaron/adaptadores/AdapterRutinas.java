package com.example.learningstrenghtaaron.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;



public class AdapterRutinas extends FirestoreRecyclerAdapter<Rutina, AdapterRutinas.ViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ArrayList<Rutina> rutinas;
    public AdapterRutinas(@NonNull FirestoreRecyclerOptions<Rutina> options) {
        super(options);
        rutinas = new ArrayList<>();
    }

    /**
     * @param holder
     * @param position
     * @param model    the model object containing the data that should be used to populate the view.
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Rutina model) {
        holder.nombreRutina.setText(model.getNombreRutina());
        holder.tipoRutina.setText(model.getTipoRutina());
        holder.creador.setText(model.getCreador());
        if(model.getTipoRutina().equals("Fuerza")) {
            holder.icono.setImageResource(R.drawable.iconofuerza);
        } else if (model.getTipoRutina().equals("Hipertrofia")) {
            holder.icono.setImageResource(R.drawable.hipertrofiaicono);
        }
        rutinas.add(model);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRutina = (TextView) itemView.findViewById(R.id.nombreRutina);
            tipoRutina = (TextView) itemView.findViewById(R.id.TipoRutina);
            icono =(ImageView) itemView.findViewById(R.id.iconoRutina);
            creador = (TextView) itemView.findViewById(R.id.CreadorRutina);
        }
    }

    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }

}
