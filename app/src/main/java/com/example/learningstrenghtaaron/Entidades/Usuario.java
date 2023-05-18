package com.example.learningstrenghtaaron.Entidades;

public class Usuario {
    private String idUsuario;
    private String fotoUsuario;
    private String nombreUsuario;
    private String tipoUsuario;
    private int edad;
    private double peso;
    private int altura;

    public Usuario(String fotoUsuario, String nombreUsuario, String tipoUsuario, int edad, double peso, int altura) {
        this.fotoUsuario = fotoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", fotoUsuario='" + fotoUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", altura=" + altura +
                '}';
    }

}
