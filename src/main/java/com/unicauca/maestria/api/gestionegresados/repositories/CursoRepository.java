package com.unicauca.maestria.api.gestionegresados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByEstudianteId(Long idEstudiante);
}
