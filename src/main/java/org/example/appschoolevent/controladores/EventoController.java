package org.example.appschoolevent.controladores;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.servicios.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@AllArgsConstructor
public class EventoController {

    private EventoService eventoService;

    @GetMapping("/todos")
    public ResponseEntity<List<EventoDTO>> obtenerTodosEventos() {
        List<EventoDTO> eventos = eventoService.obtenerTodosEventos();
        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/crear")
    public ResponseEntity<EventoDTO> crearEvento(@Valid @RequestBody EventoDTO dto) {
        EventoDTO resultado = eventoService.crearEvento(dto);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<EventoDTO>> filtrarEventos(
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) TipoCategoria categoria) {
        List<EventoDTO> eventos = eventoService.filtarEventos(fecha, categoria);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EventoDTO> obtenerEventoPorId(@PathVariable Integer id) {
        EventoDTO evento = eventoService.obtenerEventoPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EventoDTO> actualizarEvento(@PathVariable Integer id,@Valid @RequestBody EventoDTO dto) {
        EventoDTO eventoActualizado = eventoService.actualizarEvento(id, dto);
        return ResponseEntity.ok(eventoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEvento(@PathVariable Integer id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.ok("Evento eliminado correctamente");
    }
}
