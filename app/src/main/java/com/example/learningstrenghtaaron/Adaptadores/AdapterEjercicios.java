package com.example.learningstrenghtaaron.adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Map;


public class AdapterEjercicios extends FirestoreRecyclerAdapter<EjercicioRutina, AdapterEjercicios.ViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ArrayList<EjercicioRutina> ejercicios;
    public AdapterEjercicios(@NonNull FirestoreRecyclerOptions<EjercicioRutina> options) {
        super(options);
        ejercicios = new ArrayList<>();
    }

    /**
     * @param holder
     * @param position
     * @param model    the model object containing the data that should be used to populate the view.
     */

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull EjercicioRutina model) {

        for(Map.Entry<String, Integer> entry : model.getSeriesReps().entrySet()){
            View registro = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.tablerow, null, false);
            TextView nombreEjercicio = (TextView) registro.findViewById(R.id.nombreEjercicioRow);
            TextView seriesReps = (TextView) registro.findViewById(R.id.seriesRepsRow);
            TextView intensidad = (TextView) registro.findViewById(R.id.intensidadRow);
            nombreEjercicio.setText(model.getNombreEjercicio());
            seriesReps.setText(entry.getKey());
            intensidad.setText(entry.getValue() + "%");
            holder.tablaEjercicios.addView(registro);
        }
        ejercicios.add(model);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ejerciciosview, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TableLayout tablaEjercicios;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tablaEjercicios = (TableLayout) itemView.findViewById(R.id.TableLayoutEjercicios);
        }
    }

    public ArrayList<EjercicioRutina> getEjercicios() {
        return ejercicios;
    }

}
