package com.example.learningstrenghtaaron.calculadoras.calorias;

import java.text.DecimalFormat;

public class Macros {
    String calorias, prote, carbos, grasas;

    public Macros(Double calorias, Double prote, double carbos, double grasas) {
        redondear(calorias, prote, carbos, grasas);
    }

    private void redondear(Double calorias, Double prote, double carbos, double grasas) {
        DecimalFormat df = new DecimalFormat("#.00");
        this.calorias = df.format(calorias);
        this.prote = df.format(prote);
        this.grasas =df.format(grasas);
        this.carbos = df.format(carbos);
    }

    @Override
    public String toString() {
        return "\tCalorias -> " + calorias + "kcal"
                + "\n\tProteinas: " + prote + "g"
                + "\n\tCarbohidratos: " + carbos + "g"
                + "\n\tGrasas: " + grasas + "g";
    }
}
