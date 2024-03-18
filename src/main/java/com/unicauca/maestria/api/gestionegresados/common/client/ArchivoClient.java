package com.unicauca.maestria.api.gestionegresados.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;

@FeignClient(name = "msv-estudiante-docente", url = "https://ms-gestion-docentes-estudiantes-main.onrender.com")
public interface ArchivoClient {

    @GetMapping("/api/estudiantes/")
    public List<EstudianteResponseDto> obtenerEstudiantes();

    @GetMapping("/api/estudiantes/{id}")
    public InformacionGeneralResponseDto obtenerPorIdEstudiante(@PathVariable Long id);

}
