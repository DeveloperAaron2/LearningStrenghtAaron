package com.example.learningstrenghtaaron.Entidades;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {
    private String id, usuario, correo, foto, fechaNac, deporte;
    private long peso, altura;
    private Map<String, String> mapaRms;

    public Usuario() {
    }

    public Usuario(String id, String usuario, String correo, String fechaNac) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.fechaNac = fechaNac;
    }

    public Usuario(String id, String usuario, String correo, String foto, String fechaNac, String deporte, long peso, long altura, Map<String, String> mapaRms) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.foto = foto;
        this.fechaNac = fechaNac;
        this.deporte = deporte;
        this.peso = peso;
        this.altura = altura;
        this.mapaRms = mapaRms;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Usuario(Map<String, Object> datosUsuario) {
        if (datosUsuario != null) {
            this.id = (String) datosUsuario.getOrDefault("Id", "");
            this.usuario = (String) datosUsuario.getOrDefault("Usuario", "");
            this.correo = (String) datosUsuario.getOrDefault("Correo", "");
            this.foto = (String) datosUsuario.getOrDefault("Foto", "");
            this.fechaNac = (String) datosUsuario.getOrDefault("FechaNac", "");
            this.deporte = (String) datosUsuario.getOrDefault("Deporte", "");
/*            this.peso = Long.parseLong(String.valueOf(datosUsuario.getOrDefault("Peso", 0)));
            this.altura = Long.parseLong(String.valueOf(datosUsuario.getOrDefault("Altura", 0)));*/
            this.peso = Long.parseLong((String) datosUsuario.get("Peso"));
            this.altura = Long.parseLong((String) datosUsuario.get("Altura"));
            this.mapaRms = (Map<String, String>) datosUsuario.getOrDefault("MapaRms", new HashMap<String, String>());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getAltura() {
        return altura;
    }

    public void setAltura(long altura) {
        this.altura = altura;
    }

    public Map<String, String> getMapaRms() {
        return mapaRms;
    }

    public void setMapaRms(Map<String, String> mapaRms) {
        this.mapaRms = mapaRms;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", correo='" + correo + '\'' +
                ", foto='" + foto + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", deporte='" + deporte + '\'' +
                ", peso=" + peso +
                ", altura=" + altura +
                ", MapaRms=" + mapaRms +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> mapUsuario = new HashMap<>();

        mapUsuario.put("Id", id);
        mapUsuario.put("Usuario", usuario);
        mapUsuario.put("Correo", correo);
        mapUsuario.put("Foto", foto);
        mapUsuario.put("FechaNac", fechaNac);
        mapUsuario.put("Deporte", deporte);
        mapUsuario.put("Peso", peso);
        mapUsuario.put("Altura", altura);
        mapUsuario.put("MapaRms", mapaRms);

        return mapUsuario;
    }
}
