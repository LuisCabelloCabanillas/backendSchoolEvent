package org.example.appschoolevent.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.appschoolevent.DTO.UsuarioDTO;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Usuario", schema = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "fecha_de_nacimiento")
    private LocalDate fecha_de_nacimiento;

    @Column(name = "correo")
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoUsuario tipo;

    public enum TipoUsuario {
        Admin, User
    }

    @OneToMany(mappedBy = "usuario")
    private Set<Inscripcion> inscripciones;
}
