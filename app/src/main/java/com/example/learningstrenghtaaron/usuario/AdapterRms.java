package com.example.learningstrenghtaaron.usuario;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningstrenghtaaron.R;

import java.util.Map;

public class AdapterRms extends RecyclerView.Adapter<AdapterRms.ViewHolder> {
    private Map<String, String> mapaRms;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText eTRm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eTRm = itemView.findViewById(R.id.editTextNumeroRmsview);
        }

        public EditText geteTRm() {
            return eTRm;
        }
    }

    public AdapterRms(Map<String, String> mapaRms) {
        this.mapaRms = mapaRms;
    }



    @NonNull
    @Override
    public AdapterRms.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRms.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}


