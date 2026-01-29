package org.example.appschoolevent.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {

    private Integer id;

    @NotBlank(message = "Debes escribir un nombre")
    private String nombre;
    @NotBlank(message = "Debes escribir el lugar donde se realiza")
    private String lugar;

    private String requisitos;
    @NotNull(message = "Debes escribir una fecha")
    private String fecha;
    @NotBlank(message = "Debes escribir en que consiste")
    private String consiste;
    @NotBlank(message = "Debes escribir una categoria (Deportivo, Ciencia, Cultural u otros)")
    private String categoria;

}
