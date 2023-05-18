package com.example.learningstrenghtaaron.Entidades;

public class Ejercicio {

    private String grupoMuscular;
    private String nombreEjercicio;
    private String tipoEjercicio;
    private String tipoEntrenamiento;


    public Ejercicio(){}


    public Ejercicio(String nombreEjercicio, String grupoMuscular, String tipoEjercicio, String tipoEntrenamiento) {
        this.grupoMuscular = grupoMuscular;
        this.nombreEjercicio = nombreEjercicio;
        this.tipoEjercicio = tipoEjercicio;
        this.tipoEntrenamiento = tipoEntrenamiento;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public String getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public void setTipoEntrenamiento(String tipoEntrenamiento) {
        this.tipoEntrenamiento = tipoEntrenamiento;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                ", grupoMuscular='" + grupoMuscular + '\'' +
                ", nombreEjercicio='" + nombreEjercicio + '\'' +
                ", tipoEjercicio='" + tipoEjercicio + '\'' +
                ", tipoEntrenamiento='" + tipoEntrenamiento + '\'' +
                '}';
    }
}
