package org.svalero.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {

    private String id;
    private String fechaInicio;
    private String fechaFin;

}
