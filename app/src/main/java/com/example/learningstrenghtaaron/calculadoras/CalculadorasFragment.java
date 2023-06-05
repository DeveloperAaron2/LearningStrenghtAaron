package com.example.learningstrenghtaaron.calculadoras;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.learningstrenghtaaron.PantallaPrincipal;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.calculadoras.macros.CalculadoraMacrosActivity;
import com.example.learningstrenghtaaron.calculadoras.rm.CalculadoraRmActivity;

import java.util.ArrayList;

public class CalculadorasFragment extends Fragment {

    private Button button3, button4, button5, button6, button7, button8, button9, button10, button11,
            button12, button13, button14, button15, button16, button17, button18, btnRm, btnMacros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadoras, container, false);

        inicializarComponentes(view);
        inicializarCalculadoras(view);

        return view;
    }

    private void inicializarCalculadoras(View view) {
        btnRm = view.findViewById(R.id.btnCalculadoraRmFragmentCalculadoras);
        btnMacros = view.findViewById(R.id.btnCalculadoraMacrosFragmentCalculadoras);

        btnRm.setOnClickListener(view1 -> startActivity(new Intent(getContext(), CalculadoraRmActivity.class)));
        btnMacros.setOnClickListener(view1 -> startActivity(new Intent(getContext(), CalculadoraMacrosActivity.class)));
    }

    private void inicializarComponentes(View v) {
        ArrayList<Integer> botones = rellenarNumeros();
        button3 = v.findViewById(R.id.button3);
        button4 = v.findViewById(R.id.button4);
        button5 = v.findViewById(R.id.button5);
        button6 = v.findViewById(R.id.button6);
        button7 = v.findViewById(R.id.button7);
        button8 = v.findViewById(R.id.button8);
        button9 = v.findViewById(R.id.button9);
        button10 = v.findViewById(R.id.button10);
        button11 = v.findViewById(R.id.button11);
        button12 = v.findViewById(R.id.button12);
        button13 = v.findViewById(R.id.button13);
        button14 = v.findViewById(R.id.button14);
        button15 = v.findViewById(R.id.button15);
        button16 = v.findViewById(R.id.button16);
        button17 = v.findViewById(R.id.button17);
        button18 = v.findViewById(R.id.button18);

        button3.setOnClickListener(view -> {
            if (botones.contains(3))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button4.setOnClickListener(view -> {
            if (botones.contains(4))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button5.setOnClickListener(view -> {
            if (botones.contains(5))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button6.setOnClickListener(view -> {
            if (botones.contains(6))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button7.setOnClickListener(view -> {
            if (botones.contains(7))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button8.setOnClickListener(view -> {
            if (botones.contains(8))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button9.setOnClickListener(view -> {
            if (botones.contains(9))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button10.setOnClickListener(view -> {
            if (botones.contains(10))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button11.setOnClickListener(view -> {
            if (botones.contains(11))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button12.setOnClickListener(view -> {
            if (botones.contains(12))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button13.setOnClickListener(view -> {
            if (botones.contains(13))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button14.setOnClickListener(view -> {
            if (botones.contains(14))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button15.setOnClickListener(view -> {
            if (botones.contains(15))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button16.setOnClickListener(view -> {
            if (botones.contains(16))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button17.setOnClickListener(view -> {
            if (botones.contains(17))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
        button18.setOnClickListener(view -> {
            if (botones.contains(18))
                cerrarAplicacion();
            else
                Toast.makeText(getContext(), "Ese no es pringao.", Toast.LENGTH_SHORT).show();
        });
    }

    private ArrayList<Integer> rellenarNumeros() {
        ArrayList<Integer> botones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            botones.add((int) (Math.random() * (18 + 1 - 3)) + 3);
        }

        return botones;
    }

    private void cerrarAplicacion(){
        Intent intent = new Intent(getContext(), PantallaPrincipal.class);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public static CalculadorasFragment newInstance() {
        CalculadorasFragment fragment = new CalculadorasFragment();
        Bundle args = new Bundle();
        return fragment;
    }

}