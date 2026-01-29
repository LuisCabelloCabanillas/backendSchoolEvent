package org.example.appschoolevent.servicios;

import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EstadisticaDTO;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadisticaService {

    private final EventoRepository eventoRepository;

    public List<EstadisticaDTO> obtenerEstadisticas() {
        PageRequest top5 = PageRequest.of(0, 5);
        List<EstadisticaDTO> eventoInscripciones = eventoRepository.obtenerEventosIns(top5);

        if (eventoInscripciones.isEmpty()) {
            throw new ElementosNoEncontrados(
                    "No existen estadísticas de eventos"
            );
        }

        return eventoInscripciones;
    }
}
