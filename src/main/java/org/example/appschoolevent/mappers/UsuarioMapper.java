package org.example.appschoolevent.mappers;


import org.example.appschoolevent.DTO.UsuarioDTO;
import org.example.appschoolevent.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "tipo", expression = "java(org.example.appschoolevent.modelo.Usuario.TipoUsuario.valueOf(dto.getTipo()))")
    Usuario toEntity(UsuarioDTO dto);

    @Mapping(target = "tipo", expression = "java(entity.getTipo().name())")
    UsuarioDTO toDTO(Usuario entity);
}
