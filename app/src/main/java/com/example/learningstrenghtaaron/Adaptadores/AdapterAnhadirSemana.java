package com.example.learningstrenghtaaron.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.anhadir_semanas_fragment;

import java.util.ArrayList;
import java.util.List;

public class AdapterAnhadirSemana extends RecyclerView.Adapter<AdapterAnhadirSemana.ViewHolder> {

    private ArrayList<String> elementos;

    private anhadir_semanas_fragment controller;
    public AdapterAnhadirSemana(anhadir_semanas_fragment controller) {
        this.elementos = new ArrayList<>();
        elementos.add("Semana 1");
        this.controller = controller;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anhadir_semanas_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombreElemento = elementos.get(position);
        holder.nombreSemana.setText( nombreElemento);
        List<String> elementos = new ArrayList<>();
        elementos.add("Dias");
        elementos.add("Dia 1");
        elementos.add("Dia 2");
        elementos.add("Dia 3");
        elementos.add("Dia 4");
        elementos.add("Dia 5");
        elementos.add("Dia 6");
        elementos.add("Dia 7");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, elementos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerDias.setAdapter(adapter);
        holder.spinnerDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Dias")){
                    Toast.makeText(adapter.getContext(), "Seleciona un día para añadir ejercicios",Toast.LENGTH_LONG).show();
                }else
                    controller.CambiarFragment(position,elementos.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public ArrayList<String> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<String> dias) {
        this.elementos = dias;
    }
}
