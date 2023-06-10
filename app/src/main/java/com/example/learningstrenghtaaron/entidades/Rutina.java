package com.example.learningstrenghtaaron.entidades;

import java.io.Serializable;

public class Rutina implements Serializable {


    private String nombreRutina;
    private String tipoRutina;

    private String creador;

    private String acceso;

    public Rutina(){}
    public Rutina(String nombreRutina, String tipoRutina, String creador,String acceso) {
        this.nombreRutina = nombreRutina;
        this.tipoRutina = tipoRutina;
        this.creador = creador;
        this.acceso = acceso;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
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
                ", creador='" + creador + '\'' +
                ", acceso='" + acceso + '\'' +
                '}';
    }
}
