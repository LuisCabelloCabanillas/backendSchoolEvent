package org.example.appschoolevent.servicios;

import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.DTO.UsuarioDTO;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.exceptions.OperacionNoPermitida;
import org.example.appschoolevent.mappers.EventoMapper;
import org.example.appschoolevent.mappers.UsuarioMapper;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final EventoMapper eventoMapper;
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private final InscripcionRepository inscripcionRepository;

    public UsuarioDTO registrarUsuario(UsuarioDTO dto) {

        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new OperacionNoPermitida("El correo ya está registrado " + dto.getCorreo());
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario guardado= usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(guardado);
    }

    public List<EventoDTO> obtenerEventosDeUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ElementosNoEncontrados(
                        "Usuario no encontrado"));
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuarioId(idUsuario);

        return inscripciones.stream()
                .map(Inscripcion-> eventoMapper.toDTO(Inscripcion.getEvento()))
                .toList();
    }

}