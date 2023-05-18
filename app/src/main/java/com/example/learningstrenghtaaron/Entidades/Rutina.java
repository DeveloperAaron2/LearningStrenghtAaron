package com.example.learningstrenghtaaron.Entidades;

import java.io.Serializable;

public class Rutina implements Serializable {


    private String nombreRutina;
    private String tipoRutina;

    public Rutina(){}
    public Rutina(String nombreRutina, String tipoRutina) {
        this.nombreRutina = nombreRutina;
        this.tipoRutina = tipoRutina;

    }



    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public String getTipoRutina() {
        return tipoRutina;
    }

    public void setTipoRutina(String tipoRutina) {
        this.tipoRutina = tipoRutina;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "nombreRutina='" + nombreRutina + '\'' +
                ", tipoRutina='" + tipoRutina + '\'' +
                '}';
    }
}
