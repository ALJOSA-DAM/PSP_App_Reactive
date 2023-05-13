package org.svalero.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coche {

    private String id;
    private String marca;
    private String matricula;
    private String modelo;
    private boolean disponible;
    private List<Alquiler> alquileres;

    @Override
    public String toString() {
        String disponible = "";

        if (this.disponible) {
            disponible = "SI";
        } else {
            disponible = "NO";
        }

        return "Coche " + id + " ->\n" +
                '\t' + "Marca: " + "\t\t\t\t" + marca + "\n" +
                '\t' + "Matricula: " + "\t\t\t" + matricula + "\n" +
                '\t' + "Modelo: " + "\t\t\t" + modelo + "\n" +
                '\t' + "Disponible: " + "\t\t\t" + disponible;
    }
}
