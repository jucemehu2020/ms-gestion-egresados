package com.unicauca.maestria.api.gestionegresados.controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.services.empresa.EmpresaService;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class EmpresaController {
    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> crear(@Valid @RequestBody EmpresaSaveDto examenValoracion,
            BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.crear(examenValoracion, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody EmpresaSaveDto examenValoracion, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.actualizar(id, examenValoracion, result));
    }

    // @GetMapping
    // public ResponseEntity<List<EmpresaResponseDto>> listar() {
    //     return ResponseEntity.status(HttpStatus.OK).body(empresaService.listar());
    // }

    // @DeleteMapping("{id}")
    // public ResponseEntity<?> eliminar(@PathVariable Long id) {
    //     empresaService.eliminar(id);
    //     return ResponseEntity.ok().build();
    // }

    @GetMapping("listarEmpresas/{idEstudiante}")
    public ResponseEntity<List<EmpresaResponseDto>> listarEmpresasEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listarEmpresasEstudiante(idEstudiante));
    }

}
