package com.example.learningstrenghtaaron.entidades;

public class MetodosEliminados {
    /*
    private FirebaseFirestore firestore;
    public void InsertarRutina(Rutina rutina){
        for (int i=0; i < rutina.getSemanas().size();i++) {
            for(int j=0;j < rutina.getSemanas().get(i).getDias().size(); j++){
                for(int h=0;h < rutina.getSemanas().get(i).getDias().get(j).getEjercicioList().size(); h++){
                    HashMap<String,Object> ejercicios = new HashMap<>();
                    ejercicios.put("NombreEjercicio",rutina.getSemanas().get(i).getDias().get(j).getEjercicioList().get(h).getNombreEjercicio());
                    ejercicios.put("SeriesxReps",rutina.getSemanas().get(i).getDias().get(j).getEjercicioList().get(h).getSeriesRepsIntensidad());
                    firestore.collection("Rutina")
                            .document(rutina.getNombreRutina()).collection("Semanas")
                            .document("Semana" + i).collection("Dias")
                            .document("Día " + j).collection("Ejercicios")
                            .document("Ejercicio " + h).set(ejercicios).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("Rutina añadida con éxito");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Error al añadir la rutina");
                                }});
                }
            }
        }
    }
*/
}
