package com.example.learningstrenghtaaron.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;

import java.util.Map;

public class AdapterRms extends RecyclerView.Adapter<AdapterRms.ViewHolder> {
    private Object[] arrayRms;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtEj, txtSuf;
        private final EditText eTRm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEj = itemView.findViewById(R.id.txtDescripcionRmsview);
            eTRm = itemView.findViewById(R.id.editTextNumeroRmsview);
            txtSuf = itemView.findViewById(R.id.txtSufijoRmsview);
        }

        public TextView getTxtEj() {
            return txtEj;
        }

        public EditText geteTRm() {
            return eTRm;
        }

        public TextView getTxtSuf() {
            return txtSuf;
        }
    }

    public AdapterRms(Map<String, String> mapaRms) {
        this.arrayRms = mapaRms.entrySet().toArray();
    }

    @NonNull
    @Override
    public AdapterRms.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rmsview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRms.ViewHolder holder, int position) {
        String[] text = arrayRms[position].toString().split("=");
        String ejercicio = text[0];
        String rm = text[1].split(" ")[0];
        String sufijo = text[1].split(" ")[1];
        holder.getTxtEj().setText(ejercicio);
        holder.geteTRm().setText(rm);
        holder.getTxtSuf().setText(sufijo);
    }

    @Override
    public int getItemCount() {
        return arrayRms.length;
    }
}


