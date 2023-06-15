package com.example.learningstrenghtaaron.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;

import java.util.ArrayList;
import java.util.Map;

public class AdapterAnhadirEjercicios extends RecyclerView.Adapter<AdapterAnhadirEjercicios.ViewHolder> {

    private ArrayList<EjercicioRutina> elementos;

    public AdapterAnhadirEjercicios() {
        this.elementos = new ArrayList<>();

    }
    public AdapterAnhadirEjercicios(ArrayList<EjercicioRutina> elementos) {
        this.elementos = elementos;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ejerciciosview, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for (Map.Entry<String, Integer> entry : elementos.get(position).getSeriesReps().entrySet()) {
            View registro = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.tablerow, null, false);
            TextView nombreEjercicio = (TextView) registro.findViewById(R.id.nombreEjercicioRow);
            TextView seriesReps = (TextView) registro.findViewById(R.id.seriesRepsRow);
            TextView intensidad = (TextView) registro.findViewById(R.id.intensidadRow);
            nombreEjercicio.setText(elementos.get(position).getNombreEjercicio());
            seriesReps.setText(entry.getKey());
            intensidad.setText(entry.getValue() + "%");
            holder.tablaEjercicios.addView(registro);
        }

    }


    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TableLayout tablaEjercicios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tablaEjercicios = itemView.findViewById(R.id.TableLayoutEjercicios);
        }
    }

    public ArrayList<EjercicioRutina> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<EjercicioRutina> elementos) {
        this.elementos = elementos;

    }
}
