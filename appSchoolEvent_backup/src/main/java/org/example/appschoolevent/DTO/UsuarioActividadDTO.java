package org.example.appschoolevent.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioActividadDTO {

    private Integer idUsuario;
    private String nombre;
    private Long totalComentarios;
    private Long totalInscripciones;
    private Long totalGlobal;

    public UsuarioActividadDTO(Integer idUsuario,
                               String nombre,
                              Long totalComentarios,
                              Long totalInscripciones) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.totalComentarios = totalComentarios;
        this.totalInscripciones = totalInscripciones;
        this.totalGlobal = totalComentarios + totalInscripciones;
    }


}
