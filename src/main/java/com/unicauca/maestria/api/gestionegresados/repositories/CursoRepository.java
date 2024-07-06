package com.unicauca.maestria.api.gestionegresados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT tg FROM Curso tg WHERE tg.idEstudiante = ?1")
    public List<Curso> findByEstudianteId(Long idEstudiante);
}
