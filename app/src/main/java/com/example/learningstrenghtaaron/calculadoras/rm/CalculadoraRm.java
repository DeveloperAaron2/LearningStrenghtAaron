package com.example.learningstrenghtaaron.calculadoras.rm;

import java.util.HashMap;
import java.util.Map;

public class CalculadoraRm {
    private Integer peso;
    private Integer repes;
    private Map<String, Integer> mapRm;

    public CalculadoraRm(Integer peso, Integer repes) {
        this.peso = peso;
        this.repes = repes;
    }
    public Map<String, Integer> getRm(){
        mapRm = new HashMap<>();
        mapRm.put("Brzycki", rmBrzycki());
        mapRm.put("Epley", rmEpley());
        return mapRm;
    }

    private Integer rmBrzycki() {
        Integer arriba = peso * 36;
        Integer abajo = 37 - repes;
/*        if (abajo != 0)
            return arriba / abajo;
        else
            return 0;*/
        return abajo != 0 ? arriba/abajo : 0;
    }

    private Integer rmEpley() {
        return (peso * (repes + 30)) / 30;
    }
}
