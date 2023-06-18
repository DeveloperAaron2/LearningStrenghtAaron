package com.example.learningstrenghtaaron.baseDeDatos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learningstrenghtaaron.entidades.Ejercicio;
import com.example.learningstrenghtaaron.entidades.EjercicioRutina;
import com.example.learningstrenghtaaron.entidades.Rutina;
import com.example.learningstrenghtaaron.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public static Firestore getINSTANCIA() {
        return INSTANCIA;
    }

    static public Firestore getInstance() {
        if (INSTANCIA == null)
            INSTANCIA = new Firestore();
        return INSTANCIA;
    }
    public void InsertarUsuarioEnRutina(String nombreRutina){
        DocumentReference docRef = firestore.collection("Rutina").document(nombreRutina);
        if(docRef!=null){
            ArrayList<String> objetos = new ArrayList<>();
            objetos.add(auth.getCurrentUser().getUid());
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("Usuarios", objetos);
            docRef.update(datos);
        }
    }
    public void BorrarRutina(String nombreRutina){
        DocumentReference docRefRutina = firestore.collection("Rutina").document(nombreRutina);
        docRefRutina.delete();
        System.out.println(nombreRutina);
        firestore.collection("EjercicioRutina")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("NombreRutina").equals(nombreRutina))
                                    firestore.collection("EjercicioRutina").document(document.getString("NombreRutina")).delete();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
    }});
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

    public void InsertarRutina(Rutina rutina) {
        HashMap<String, String> datos = new HashMap<>();
        datos.put("tipoRutina", rutina.getTipoRutina());
        datos.put("nombreRutina",rutina.getNombreRutina());
        datos.put("acceso",rutina.getAcceso());
        datos.put("creador",rutina.getCreador());

        firestore.collection("Rutina").document(rutina.getNombreRutina()).set(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    }
    public void InsertarEjerciciosRutina(ArrayList<EjercicioRutina> ejercicioRutina){
        for (EjercicioRutina ejercicioRutina1 : ejercicioRutina) {
            HashMap<String,Object> ejercicios = new HashMap<>();
            ejercicios.put("NombreEjercicio",ejercicioRutina1.getNombreEjercicio());
            ejercicios.put("NombreRutina",ejercicioRutina1.getNombreRutina());
            ejercicios.put("NumeroDia",ejercicioRutina1.getNumeroDia());
            ejercicios.put("NumeroSemana",ejercicioRutina1.getNumeroSemana());
            ejercicios.put("SeriesReps",ejercicioRutina1.getSeriesReps());
            firestore.collection("EjercicioRutina").add(ejercicios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                    System.out.println(mapUsuario.get("Id") + " añadido");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al insertar " + mapUsuario.get("Id"));
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
    public Usuario getUsuario(){
        return usuario;
    }

    public Usuario getUsuario(String uid) {
        firestore.collection("Usuario")
                .document(uid)
                .get()
                .addOnSuccessListener(documentReference -> {
                    usuario = new Usuario(documentReference.getData());
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al recoger al usuario con id " + uid);
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


