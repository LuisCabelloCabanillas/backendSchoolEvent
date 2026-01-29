package org.example.appschoolevent.controladores;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.DTO.UsuarioDTO;
import org.example.appschoolevent.servicios.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioServicio;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO resultado = usuarioServicio.registrarUsuario(dto);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<List<EventoDTO>> obtenerEventosDeUsuario(@PathVariable Integer id) {
        List<EventoDTO> eventos = usuarioServicio.obtenerEventosDeUsuario(id);
        return ResponseEntity.ok(eventos);
    }
}
