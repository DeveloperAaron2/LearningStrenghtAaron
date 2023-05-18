package com.example.learningstrenghtaaron.calculadoras.calorias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.learningstrenghtaaron.R;

public class InfoActivity extends AppCompatActivity {
    private LinearLayout layoutInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_calorias_info);

        layoutInfo = findViewById(R.id.layoutInfoCalculadoraCalorias);
        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}