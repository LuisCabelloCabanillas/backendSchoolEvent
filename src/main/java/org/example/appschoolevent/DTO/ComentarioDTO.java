package org.example.appschoolevent.DTO;

import jakarta.persistence.*;
import lombok.Data;
import org.example.appschoolevent.modelo.Usuario;

@Data

public class ComentarioDTO {

    private Integer id;
    private String opinion;
    private Usuario usuario;

}
