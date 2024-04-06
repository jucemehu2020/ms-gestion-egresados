package com.unicauca.maestria.api.gestionegresados.controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.services.curso.CursoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/curso")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoSaveDto> crear(@Valid @RequestBody CursoSaveDto examenValoracion,
            BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursoService.crear(examenValoracion, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoSaveDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoSaveDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody CursoSaveDto examenValoracion, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursoService.actualizar(id, examenValoracion, result));
    }

    @GetMapping("listarCursos/{id}")
    public ResponseEntity<List<CursoSaveDto>> listar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listar(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
