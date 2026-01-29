package org.example.appschoolevent.mappers;

import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.modelo.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoMapper {


    @Mapping(target = "categoria", expression = "java(org.example.appschoolevent.enums.TipoCategoria.valueOf(dto.getCategoria()))")
    Evento toEntity(EventoDTO dto);

    default EventoDTO toDTO(Evento evento){
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setNombre(evento.getNombre());
        dto.setLugar(evento.getLugar());
        dto.setRequisitos(evento.getRequisitos());
        dto.setFecha(evento.getFecha().toString());
        dto.setConsiste(evento.getConsiste());
        dto.setCategoria(evento.getCategoria().name());
        return dto;
    }

}
