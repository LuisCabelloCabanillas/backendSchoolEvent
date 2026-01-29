package org.example.appschoolevent.repositorio;

import jakarta.transaction.Transactional;
import org.example.appschoolevent.modelo.Fotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FotosRepository extends JpaRepository<Fotos, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Fotos f WHERE f.evento.id = :idEvento")
    void borrarFotosPorEvento(@Param("idEvento") Integer idEvento);
}
