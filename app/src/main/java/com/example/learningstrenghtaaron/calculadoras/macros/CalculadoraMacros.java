package com.example.learningstrenghtaaron.calculadoras.macros;

import java.util.HashMap;
import java.util.Map;

public class CalculadoraMacros {
    Map<String, Macros> mapMacros;
    Integer peso, altura, edad;
    String sexo, objetivo;
    Double actividad;

    public CalculadoraMacros(Integer peso, Integer altura, Integer edad, String sexo, Double actividad, String objetivo) {
        this.peso = peso;
        this.altura = altura;
        this.edad = edad;
        this.sexo = sexo;
        this.actividad = actividad;
        this.objetivo = objetivo;
    }

    public Map<String, Macros> getMacros(){
        mapMacros = new HashMap<>();

        Double calorias = calculoCalorias();

        if (objetivo.equals("Perder peso intenso")) {
            mapMacros.put("Definicion", calculoMacros(calorias - 500, "Definicion"));

        } else if (objetivo.equals("Perder peso moderado")) {
            mapMacros.put("Definicion", calculoMacros(calorias - 250, "Definicion"));

        } else if (objetivo.equals("Mantener peso")) {
            mapMacros.put("Mantenimiento", calculoMacros(calorias, "Mantenimiento"));

        } else if (objetivo.equals("Ganar peso moderado")){
            mapMacros.put("Volumen", calculoMacros(calorias + 250, "Volumen"));

        } else if (objetivo.equals("Ganar peso intenso")){
            mapMacros.put("Volumen", calculoMacros(calorias + 500, "Volumen"));

        } else {
            mapMacros.put("Volumen", calculoMacros(calorias + 300, "Volumen"));
            mapMacros.put("Definicion", calculoMacros(calorias - 300, "Definicion"));
            mapMacros.put("Mantenimiento", calculoMacros(calorias, "Mantenimiento"));
        }
        return mapMacros;
    }

    private Double calculoCalorias() {
        Double calorias;
        calorias = 10 * peso + 6.25 * altura - 5 * edad;
        if (sexo.equals("H"))
            calorias += 5;
        else
            calorias -= 161;
        return calorias * actividad;
    }

    private Macros calculoMacros(Double calorias, String tipo) {
        Double prote, grasas, carbos;
        if (tipo.equals("Volumen")){
            prote = peso * 2d; // gramos de proteina
            grasas = calorias * 0.3; // calorias de grasas
            carbos = calorias - prote * 4 - grasas; // calorias de carbos
        } else if(tipo.equals("Definicion")) {
            prote = peso * 2.4;
            grasas = calorias * 0.3;
            carbos = calorias - prote * 4 - grasas;
        } else {
            prote = peso * 2.4;
            grasas = calorias * 0.3;
            carbos = calorias - prote * 4 - grasas;
        }
        return new Macros(calorias, prote, carbos / 4, grasas / 9); // gramos de cada
    }

}
