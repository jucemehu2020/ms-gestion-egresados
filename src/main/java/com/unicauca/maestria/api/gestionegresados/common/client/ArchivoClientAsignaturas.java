package com.unicauca.maestria.api.gestionegresados.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;

@FeignClient(name = "gestion-asignaturas-ms", url = "http://localhost:8021")
public interface ArchivoClientAsignaturas {

    @GetMapping("/api/asignaturas/")
    public List<ListadoAsignaturasDto> listarAsignaturas();

    
    @GetMapping("/api/asignaturas/{id}")
    public ListadoAsignaturasDto listarAsignaturaPorId(@PathVariable Long id);

}
