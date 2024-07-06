package com.unicauca.maestria.api.gestionegresados.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudiantesResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;
import com.unicauca.maestria.api.gestionegresados.services.informacion_general.InformacionGeneralService;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/informacion_general")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class InformacionGeneralController {

    private final InformacionGeneralService informacionGeneralService;

    @GetMapping("/")
    public ResponseEntity<List<InformacionEstudiantesResponseDto>> obtenerEstudiantes() {
        return ResponseEntity.status(HttpStatus.OK).body(informacionGeneralService.obtenerEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacionGeneralResponseDto> buscarEstudiante(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(informacionGeneralService.buscarEstudiante(id));
    }
}
