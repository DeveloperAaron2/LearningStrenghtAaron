package com.example.learningstrenghtaaron.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterSemanas extends RecyclerView.Adapter<AdapterSemanas.ViewHolder> {

    private int numeroSemanas;
    private ArrayList<String> semanas;

    public AdapterSemanas(int numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
        this.semanas = new ArrayList<>();
        rellenaSemanas();
    }

    private void rellenaSemanas() {
        for (int i = 0; i <= numeroSemanas; i++) {
            semanas.add("Semana " + (i +1));
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
        String nombreSemana = semanas.get(position);
        holder.nombreSemana.setText("Semana: " + nombreSemana);
    }

    @Override
    public int getItemCount() {
        return semanas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreSemana;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreSemana = itemView.findViewById(R.id.nombreSemana);

        }
    }
}
