package com.example.learningstrenghtaaron.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.R;

import java.util.ArrayList;
import java.util.List;

public class AdaterAnhadirEjercicios extends RecyclerView.Adapter<AdaterAnhadirEjercicios.ViewHolder> {

    private ArrayList<EjercicioRutina> elementos;


    public AdaterAnhadirEjercicios() {
        this.elementos = new ArrayList<>();

    }

    @NonNull
    @Override
    public AdaterAnhadirEjercicios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anhadir_semanas_view, parent, false);
        return new AdaterAnhadirEjercicios.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaterAnhadirEjercicios.ViewHolder holder, int position) {
        holder.nombreSemana.setText(elementos.get(position).getNombreEjercicio());
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreSemana;
        private Spinner spinnerDias;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreSemana = itemView.findViewById(R.id.AnhadirNombreSemana);
            this.spinnerDias = itemView.findViewById(R.id.spinnerDias);

        }
    }

    public ArrayList<EjercicioRutina> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<EjercicioRutina> dias) {
        this.elementos = dias;
    }
}
