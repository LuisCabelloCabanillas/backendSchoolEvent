package org.example.appschoolevent.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Locale;

@Data
public class UsuarioDTO {

    private Integer id;
    @NotBlank(message = "Debes escribir un nombre")
    private String nombre;
    @NotBlank(message = "Debes escribir los apellidos")
    private String apellido;
    @NotBlank(message = "Debes introducir una contraseña")
    private String contrasena;
    @NotNull(message = "Debes una fecha")
    private LocalDate fecha_de_nacimiento;
    @NotBlank(message = "Debes escribir un correo")
    private String correo;
@Pattern(
        regexp = "Admin|User",
        message = "El tipo debe ser Admin o User"
)
private String tipo;
}
