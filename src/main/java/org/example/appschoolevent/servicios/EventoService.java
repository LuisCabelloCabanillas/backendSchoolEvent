package org.example.appschoolevent.servicios;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.mappers.EventoMapper;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.FotosRepository;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EventoService {

    private EventoRepository eventoRepository;
    private EventoMapper eventoMapper;
    private FotosRepository fotosRepository;
    private InscripcionRepository inscripcionRepository;

    public List<EventoDTO> obtenerTodosEventos() {
        List<EventoDTO> eventos = eventoRepository.findAll().stream()
                .map(eventoMapper::toDTO)
                .toList();

        if (eventos.isEmpty()) {
            throw new ElementosNoEncontrados("No se encontraron eventos");
        }
        return eventos;
    }

    public EventoDTO crearEvento(EventoDTO dto) {
        Evento evento = eventoMapper.toEntity(dto);
        Evento guardado = eventoRepository.save(evento);

        return eventoMapper.toDTO(guardado);
    }

    public List<EventoDTO> filtarEventos(LocalDate fecha, TipoCategoria categoria) {
        List<EventoDTO> eventos = eventoRepository.filtrarEventos(fecha, categoria).stream()
                .map(eventoMapper::toDTO)
                .toList();

        if (eventos.isEmpty()) {
            throw new ElementosNoEncontrados(
                    "No se encontraron eventos con los filtros proporcionados");
        }
        return eventos;
    }

    public Evento obtenerEventoPorIdEntidad(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ElementosNoEncontrados(
                        "Evento no encontrado con ID: " + id));
    }

    public EventoDTO obtenerEventoPorId(Integer id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ElementosNoEncontrados(
                        "Evento no encontrado con ID: " + id));

        return eventoMapper.toDTO(evento);
    }

    public EventoDTO actualizarEvento(Integer id, EventoDTO dto) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new ElementosNoEncontrados(
                        "No se puede actualizar. Evento no encontrado con ID: " + id));

        eventoExistente.setNombre(dto.getNombre());
        eventoExistente.setLugar(dto.getLugar());
        eventoExistente.setFecha(LocalDate.parse(dto.getFecha()));
        eventoExistente.setCategoria(TipoCategoria.valueOf(dto.getCategoria()));
        eventoExistente.setRequisitos(dto.getRequisitos());
        eventoExistente.setConsiste(dto.getConsiste());

        Evento actualizado = eventoRepository.save(eventoExistente);
        return eventoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarEvento(Integer id) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new ElementosNoEncontrados(
                        "No se puede eliminar. Evento no encontrado con ID: " + id));

        fotosRepository.borrarFotosPorEvento(id);
        inscripcionRepository.deleteByEventoId(id);
        eventoRepository.delete(eventoExistente);
    }
}
