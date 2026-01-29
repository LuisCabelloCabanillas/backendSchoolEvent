package org.example.appschoolevent.repositorio;

import org.example.appschoolevent.DTO.EstadisticaDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.modelo.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Optional<Evento> findById(Integer id);


    @Query(value = "select e from Evento e " +
            "where (:fecha is null or e.fecha = :fecha) " +
            "and (:categoria is null or e.categoria = :categoria)")
    List<Evento> filtrarEventos(@Param("fecha") LocalDate fecha,
                                @Param("categoria") TipoCategoria categoria);

    @Query("""
        select new org.example.appschoolevent.DTO.EstadisticaDTO(
            e.id,
            e.nombre,
            e.lugar,
            e.requisitos,
            e.consiste,
            e.categoria,
            COUNT(i.id)
       )
       FROM Evento e
       left join Inscripcion i on i.evento.id = e.id
       group by e.id, e.nombre, e.lugar, e.requisitos, e.fecha, e.consiste, e.categoria
       order by COUNT(i.id) desc
       """)
    List<EstadisticaDTO> obtenerEventosIns(org.springframework.data.domain.Pageable pageable);
}
