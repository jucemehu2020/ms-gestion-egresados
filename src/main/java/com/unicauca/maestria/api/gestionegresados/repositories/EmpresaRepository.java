package com.unicauca.maestria.api.gestionegresados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
