package org.example.appschoolevent.repositorio;

import org.example.appschoolevent.modelo.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentaroRepository extends JpaRepository<Comentario, Integer> {
}
