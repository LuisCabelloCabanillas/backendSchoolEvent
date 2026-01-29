package org.example.appschoolevent.mappers;

import org.example.appschoolevent.DTO.InscripcionDTO;
import org.example.appschoolevent.modelo.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    @Mapping(target = "idUsuario", source = "usuario.id")
    @Mapping(target = "idEvento", source = "evento.id")
    InscripcionDTO toDTO(Inscripcion inscripcion);

}
