package com.unicauca.maestria.api.gestionegresados.controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
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

    @GetMapping("/listarCursosRegistrados")
    public ResponseEntity<List<ListadoAsignaturasDto>> listarCursosExistentes() {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listarCursosExistentes());
    }

    @PostMapping
    public ResponseEntity<CursosResponseDto> crear(@Valid @RequestBody CursoSaveDto examenValoracion,
            BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursoService.crear(examenValoracion, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosResponseDto> obtenerInformacionCurso(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.obtenerInformacionCurso(id));
    }

    @GetMapping("listarCursosDictados/{id}")
    public ResponseEntity<List<CursosResponseDto>> listarCursosDictados(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.listarCursosDictados(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursosResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody CursoSaveDto examenValoracion, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursoService.actualizar(id, examenValoracion, result));
    }

    // @DeleteMapping("{id}")
    // public ResponseEntity<?> eliminar(@PathVariable Long id) {
    //     cursoService.eliminar(id);
    //     return ResponseEntity.ok().build();
    // }
}
