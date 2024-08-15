package com.unicauca.maestria.api.gestionegresados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("SELECT tg FROM Empresa tg WHERE tg.idEstudiante = ?1")
    public List<Empresa> findByEstudianteId(Long idEstudiante);
}
