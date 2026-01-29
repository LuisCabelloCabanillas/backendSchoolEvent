package org.example.appschoolevent.servicios;

import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.UsuarioActividadDTO;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioActividadService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioActividadDTO obtenerUsuarioMasActivo() {
        PageRequest top = PageRequest.of(0, 1);
        var resultado = usuarioRepository.obtenerUsuarioMasActivo(top);

        if (resultado.isEmpty()) {
            throw new ElementosNoEncontrados("No se encontraron usuarios con actividad");
        }
        return resultado.get(0);
    }

}
