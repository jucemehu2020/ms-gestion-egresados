package com.unicauca.maestria.api.gestionegresados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
