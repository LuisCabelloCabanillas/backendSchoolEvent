package org.example.appschoolevent.repositorio;

import jakarta.transaction.Transactional;
import org.example.appschoolevent.modelo.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    boolean existsByUsuarioIdAndEventoId(Integer idUsuario, Integer idEvento);
    List<Inscripcion> findByUsuarioId(Integer idUsuario);

    @Modifying
    @Transactional
    @Query("DELETE FROM Inscripcion i WHERE i.evento.id = :idEvento")
    void deleteByEventoId(@Param("idEvento") Integer idEvento);

}
