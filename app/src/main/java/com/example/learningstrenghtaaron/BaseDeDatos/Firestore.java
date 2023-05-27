package com.example.learningstrenghtaaron.BaseDeDatos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.learningstrenghtaaron.Entidades.Ejercicio;
import com.example.learningstrenghtaaron.Entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.Entidades.Rutina;
import com.example.learningstrenghtaaron.Entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Firestore {
    private Usuario usuario;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    static private Firestore INSTANCIA;

    private Firestore() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    static public Firestore getInstance() {
        if (INSTANCIA == null)
            INSTANCIA = new Firestore();
        return INSTANCIA;
    }

    public void InsertarEjercicio(Ejercicio ejercicio) {
        Map<String, Object> map = new HashMap<>();
        map.put("grupoMuscular", ejercicio.getGrupoMuscular());
        map.put("tipoEjercicio", ejercicio.getTipoEjercicio());
        map.put("tipoEntrenamiento", ejercicio.getTipoEntrenamiento());
        firestore.collection("Ejercicio").document(ejercicio.getNombreEjercicio()).set(map).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error al insertar " + ejercicio.toString());
            }
        });
    }

    public void InsertarRutina(Rutina rutina, ArrayList<EjercicioRutina> ejercicioRutina) {
        HashMap<String, String> tiporutina = new HashMap<>();
        tiporutina.put("TipoRutina", rutina.getTipoRutina());
        firestore.collection("Rutina").document(rutina.getNombreRutina()).set(tiporutina).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println(task + "añadido correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error al añadir: " + e.toString());
            }
        });
        for (EjercicioRutina ejercicioRutina1 : ejercicioRutina) {
            HashMap<String,Object> ejercicios = new HashMap<>();
            ejercicios.put("NombreEjercicio",ejercicioRutina1.getNombreEjercicio());
            ejercicios.put("NombreRutina",ejercicioRutina1.getNombreRutina());
            ejercicios.put("NumeroDia",ejercicioRutina1.getNumeroDia());
            ejercicios.put("NumeroSemana",ejercicioRutina1.getNumeroSemana());
            ejercicios.put("SeriesReps",ejercicioRutina1.getSeriesXReps());
            firestore.collection("EjerciciosRutina").add(ejercicios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    System.out.println("ejercicio: " + documentReference.toString() + " añadido con éxito");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("error al añadir el ejercicio");
                }
            });
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        insertarUsuario(usuario);
    }

    public void insertarUsuario(Usuario usuario) {
        firestore.collection("Usuario")
                .document(usuario.getId())
                .set(usuario.toMap())
                .addOnSuccessListener(documentReference -> {
                    System.out.println(usuario + " añadido");
                })
                .addOnFailureListener(e -> {
                    System.out.println("error al insertar " + usuario);
                    Log.w(TAG, "Error al registrar los datos del usuario: " + e.getMessage());
                });
    }

    public void insertarUsuario(Map<String, Object> mapUsuario) {
        firestore.collection("Usuario")
                .document((String) mapUsuario.get("Id"))
                .set(mapUsuario)
                .addOnSuccessListener(documentReference -> {
                    System.out.println(usuario + " añadido");
                })
                .addOnFailureListener(e -> {
                    System.out.println("error al insertar " + usuario);
                    Log.w(TAG, "Error al registrar los datos del usuario: " + e.getMessage());
                });
    }

    public void borrarUsuario(String uid) {
        firestore.collection("Usuario")
                .document(uid)
                .delete()
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Usuario " + uid + " eliminado");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al eliminar usuario");
                    Log.w(TAG, "Error al eliminar los datos del usuario: " + e.getMessage());
                });
    }

    public Usuario getUsuario(String uid) {
        firestore.collection("Usuario")
                .document(uid)
                .get()
                .addOnSuccessListener(documentReference -> {
                    usuario = new Usuario(documentReference.getData());
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al coger al usuario con id " + uid);
                    Log.w(TAG, "Error al coger los datos del usuario con id " + uid + ": " + e.getMessage());
                });
        return usuario;
    }

    public void InsertarEjerciciosDeUsuario(String idUsuario, String nombreUsuario, Map<String, Double> ejerciciosyrm) {
        CollectionReference coleccionEjercicios = firestore.collection("EjerciciosUsuario").document(idUsuario).collection("EjerciciosUsuario");
        for (String nombreEjercicio : ejerciciosyrm.keySet()) {
            Map<String, Object> ejerciciosUsuario = new HashMap<>();
            ejerciciosUsuario.put("RM", ejerciciosyrm.get(nombreEjercicio));
            coleccionEjercicios.document(nombreEjercicio).set(ejerciciosUsuario).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("error al introducir los ejercicios del usuario");
                }
            });
        }
    }
    /*
    public ArrayList<Ejercicio> RecogerEjercicios(){

        ArrayList<Ejercicio> ejercicios = new ArrayList<>();

        CollectionReference listaEjercicios = firestore.collection("Ejercicio");
        listaEjercicios.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ejercicio : queryDocumentSnapshots) {
                    System.out.println("ejercicio " + ejercicio.getString("grupoMuscular"));
                    Ejercicio ejercicioNuevo = new Ejercicio(ejercicio.getId(), ejercicio.getString("grupoMuscular"),ejercicio.getString(" tipoEjercicio"),ejercicio.getString("tipoEntrenamiento"));
                    ejercicios.add(ejercicioNuevo);
                    System.out.println(ejercicios.size());
                }
            }
        });

        return ejercicios;
    }*/

}

