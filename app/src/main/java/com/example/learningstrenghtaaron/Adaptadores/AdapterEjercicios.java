package com.example.learningstrenghtaaron.Adaptadores;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Map;


public class AdapterEjercicios extends FirestoreRecyclerAdapter<EjercicioRutina, AdapterEjercicios.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ArrayList<EjercicioRutina> ejercicios;
    public AdapterEjercicios(@NonNull FirestoreRecyclerOptions<EjercicioRutina> options) {
        super(options);
        this.ejercicios = new ArrayList<>();
        System.out.println("seguro que esto si lo veo");
    }

    /**
     * @param holder
     * @param position
     * @param model    the model object containing the data that should be used to populate the view.
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull EjercicioRutina model) {
        System.out.println(model.toString());
        Log.d("AdapterEjercicios", "onBindViewHolder: position = " + position);
        for(Map.Entry<String, Integer> entry : model.getSeriesXReps().entrySet()){
            InsertaFilas(holder, model,entry);
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
    private void InsertaFilas(@NonNull ViewHolder holder, @NonNull EjercicioRutina model, Map.Entry<String, Integer> entry) {
        TableRow newRow = new TableRow(holder.itemView.getContext());
        InsertaColumnas(holder, model, newRow, entry);
        holder.tablaEjercicios.addView(newRow);
    }
    private void InsertaColumnas(@NonNull ViewHolder holder, @NonNull EjercicioRutina model, TableRow newRow,Map.Entry<String, Integer> entry) {
        newRow.setBackgroundColor(Color.WHITE);
        newRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        TextView nombreEjercicio = new TextView(holder.itemView.getContext());
        nombreEjercicio.setText(model.getNombreEjercicio());
        nombreEjercicio.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        newRow.addView(nombreEjercicio);

        TextView seriesxreps= new TextView(holder.itemView.getContext());
        seriesxreps.setText(entry.getKey());
        seriesxreps.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        newRow.addView(seriesxreps);
        TextView intensidad= new TextView(holder.itemView.getContext());
        intensidad.setText(entry.getValue());
        intensidad.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        newRow.addView(intensidad);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TableLayout tablaEjercicios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tablaEjercicios = (TableLayout) itemView.findViewById(R.id.TableLayoutEjercicios);
            System.out.println("esto deberia verse");
        }
    }
    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    @Override
    public EjercicioRutina getItem(int position) {
        return ejercicios.get(position);
    }

    public ArrayList<EjercicioRutina> getEjercicios() {
        return ejercicios;
    }

}
