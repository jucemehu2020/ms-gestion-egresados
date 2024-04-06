package com.unicauca.maestria.api.gestionegresados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByEstudianteId(Long idEstudiante);
}
