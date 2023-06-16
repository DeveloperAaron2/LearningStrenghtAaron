package com.example.learningstrenghtaaron.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;

import java.util.ArrayList;

public class AdapterSemanasDias extends RecyclerView.Adapter<AdapterSemanasDias.ViewHolder> {

    private int numeroElementos;
    private ArrayList<String> elementos;

    public AdapterSemanasDias(int numeroSemanas, String tipoAdaptador) {
        this.numeroElementos = numeroSemanas;
        this.elementos = new ArrayList<>();
        if(tipoAdaptador.equals("Semanas"))
            rellenaSemanas();
        else if (tipoAdaptador.equals("Dias")) {
            RellenaDias();
        }
    }

    private void RellenaDias() {
        if (numeroElementos==0){
            elementos.add("Día Vacío");
        }
        else{
        for (int i = 0; i < numeroElementos; i++) {
            elementos.add("Día " + (i +1));
        }}
    }

    private void rellenaSemanas() {
        if (numeroElementos==0){
            elementos.add("Rutina Vacía");
        }
        else{
        for (int i = 0; i < numeroElementos; i++) {
            elementos.add("Semana " + (i +1));
        }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.semanasview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombreElemento = elementos.get(position);
        holder.nombreSemana.setText( nombreElemento);
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreSemana;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreSemana = itemView.findViewById(R.id.nombreSemana);

        }
    }

    public ArrayList<String> getElementos() {
        return elementos;
    }
}
