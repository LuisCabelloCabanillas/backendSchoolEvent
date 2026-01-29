package org.example.appschoolevent.repositorio;

import org.example.appschoolevent.DTO.EstadisticaDTO;
import org.example.appschoolevent.DTO.UsuarioActividadDTO;
import org.example.appschoolevent.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByCorreo(String correo);


    @Query("""
        select new org.example.appschoolevent.DTO.UsuarioActividadDTO(
           u.id,
           u.nombre,
           COUNT(DISTINCT c.id),
           COUNT(DISTINCT i.id)
       )
       FROM Usuario u
       left join Inscripcion i on i.usuario.id = u.id
       left join Comentario c on c.usuario.id = u.id
       group by u.id, u.nombre
       order by (COUNT(DISTINCT c.id) + COUNT(DISTINCT i.id)) DESC
       """)

    List<UsuarioActividadDTO> obtenerUsuarioMasActivo(org.springframework.data.domain.Pageable pageable);

}