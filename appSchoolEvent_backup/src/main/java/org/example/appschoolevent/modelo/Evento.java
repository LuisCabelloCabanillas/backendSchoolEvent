package org.example.appschoolevent.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.appschoolevent.enums.TipoCategoria;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "eventos", schema = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "lugar")
    private String lugar;

    @Column(name = "requisitos")
    private String requisitos;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "consiste")
    private String consiste;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private TipoCategoria categoria;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.REMOVE)
    private Set<Inscripcion> inscripciones;

}
