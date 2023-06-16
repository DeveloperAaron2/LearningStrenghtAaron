package com.example.learningstrenghtaaron.entidades;

import java.io.Serializable;
import java.util.HashMap;

public class EjercicioRutina implements Serializable {

    private String NombreEjercicio;
    private String NombreRutina;
    private int NumeroDia;
    private int NumeroSemana;
    private HashMap<String,Integer> SeriesReps;
    public EjercicioRutina(){}
    public EjercicioRutina(String nombreEjercicio, String nombreRutina, int numeroDia, int numeroSemana, HashMap<String, Integer> seriesXReps) {
        NombreEjercicio = nombreEjercicio;
        NombreRutina = nombreRutina;
        NumeroDia = numeroDia;
        NumeroSemana = numeroSemana;
        SeriesReps = seriesXReps;
    }

    public String getNombreEjercicio() {
        return NombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        NombreEjercicio = nombreEjercicio;
    }

    public String getNombreRutina() {
        return NombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        NombreRutina = nombreRutina;
    }

    public int getNumeroDia() {
        return NumeroDia;
    }

    public void setNumeroDia(int numeroDia) {
        NumeroDia = numeroDia;
    }

    public int getNumeroSemana() {
        return NumeroSemana;
    }

    public void setNumeroSemana(int numeroSemana) {
        NumeroSemana = numeroSemana;
    }

    public HashMap<String, Integer> getSeriesReps() {
        return SeriesReps;
    }

    public void setSeriesReps(HashMap<String, Integer> seriesReps) {
        SeriesReps = seriesReps;
    }



    @Override
    public String toString() {
        return "EjercicioRutina{" +
                "NombreEjercicio='" + NombreEjercicio + '\'' +
                ", NombreRutina='" + NombreRutina + '\'' +
                ", NumeroDia=" + NumeroDia +
                ", NumeroSemana=" + NumeroSemana +
                ", SeriesXReps=" + SeriesReps +
                '}';
    }

}

