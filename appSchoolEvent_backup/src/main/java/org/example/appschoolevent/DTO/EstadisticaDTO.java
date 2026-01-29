package org.example.appschoolevent.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.appschoolevent.enums.TipoCategoria;


@Data
@Getter
@Setter
@AllArgsConstructor

public class EstadisticaDTO {
    private Integer idEvento;
    private String nombre;
    private String lugar;
    private String requisitos;
    private String consiste;
    private TipoCategoria categoria;
    private Long total_asistente;

}