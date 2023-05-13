package org.svalero.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private List<Alquiler> alquileres;

    @Override
    public String toString() {
        return "Usuario " + dni + " ->\n" +
                '\t' + "Nombre: " + "\t\t\t\t" + nombre + "\n" +
                '\t' + "Apellidos: " + "\t\t\t" + apellidos + "\n" +
                '\t' + "Fecha de nacimiento: " + '\t' + fechaNacimiento;
    }
}
