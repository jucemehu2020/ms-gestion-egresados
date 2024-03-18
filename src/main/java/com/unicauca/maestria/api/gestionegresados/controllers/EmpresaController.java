package com.unicauca.maestria.api.gestionegresados.controllers;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.unicauca.maestria.api.gestionegresados.dtos.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.services.empresa.EmpresaService;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class EmpresaController {
    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaSaveDto> crear(@Valid @RequestBody EmpresaSaveDto examenValoracion,
            BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.crear(examenValoracion, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaSaveDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaSaveDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody EmpresaSaveDto examenValoracion, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaService.actualizar(id, examenValoracion, result));
    }
}
