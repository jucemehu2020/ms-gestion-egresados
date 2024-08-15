package com.unicauca.maestria.api.gestionegresados.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;

@FeignClient(name = "ms-estudiante-docente-experto", url = "http://ms-estudiante-docente-expert:8082", configuration = FeignConfig.class)
public interface ArchivoClient {

    @GetMapping("/api/estudiantes/")
    public List<EstudianteResponseDto> obtenerEstudiantes();

    @GetMapping("/api/estudiantes/{id}")
    public EstudianteResponseDto obtenerPorIdEstudiante(@PathVariable Long id);

}
