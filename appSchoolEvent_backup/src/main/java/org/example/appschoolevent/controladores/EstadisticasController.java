package org.example.appschoolevent.controladores;

import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EstadisticaDTO;
import org.example.appschoolevent.servicios.EstadisticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
@AllArgsConstructor
public class EstadisticasController {
    private final EstadisticaService estadisticaService;

    @GetMapping("/eventos")
    public ResponseEntity<List<EstadisticaDTO>> obtenerEstadisticas() {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticas());
    }
}
