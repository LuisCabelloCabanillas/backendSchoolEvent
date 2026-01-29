package org.example.appschoolevent.servicios;


import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.InscripcionDTO;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.exceptions.OperacionNoPermitida;
import org.example.appschoolevent.mappers.InscripcionMapper;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscripcionMapper inscripcionMapper;

    public InscripcionDTO inscribirUsuario(Integer idUsuario, Integer idEvento) {

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ElementosNoEncontrados("Evento no encontrado"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ElementosNoEncontrados("Usuario no encontrado"));

        boolean yaInscrito = inscripcionRepository.existsByUsuarioIdAndEventoId(idUsuario, idEvento);
        if (yaInscrito) {
            throw new OperacionNoPermitida("El usuario ya está inscrito en este evento");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEvento(evento);
        inscripcion.setUsuario(usuario);

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        return inscripcionMapper.toDTO(inscripcionGuardada);

    }
}
