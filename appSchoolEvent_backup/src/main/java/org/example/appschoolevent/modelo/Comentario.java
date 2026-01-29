package org.example.appschoolevent.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comentarios", schema = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "opinion")
    private String opinion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
