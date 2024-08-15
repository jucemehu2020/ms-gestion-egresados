package com.unicauca.maestria.api.gestionegresados.controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.services.empresa.EmpresaService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class EmpresaController {
    private final EmpresaService empresaService;

    @PostMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<EmpresaResponseDto> crear(@Valid @RequestBody EmpresaSaveDto examenValoracion,
            BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.crear(examenValoracion, result));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<EmpresaResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<EmpresaResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody EmpresaSaveDto examenValoracion, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.actualizar(id, examenValoracion, result));
    }

    @GetMapping("/listarEmpresas/{idEstudiante}")
    // @PreAuthorize("hasRole('ESTUDIANTE') or hasRole('COORDINADOR')")
    public ResponseEntity<List<EmpresaResponseDto>> listarEmpresasEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listarEmpresasEstudiante(idEstudiante));
    }

}
