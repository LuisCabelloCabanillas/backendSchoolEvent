package org.example.appschoolevent.modelo;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "fotos", schema = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "foto")
    private String foto;

    @ManyToOne
    @JoinColumn(name = "id_eventos", nullable = false)
    private Evento evento;
}
