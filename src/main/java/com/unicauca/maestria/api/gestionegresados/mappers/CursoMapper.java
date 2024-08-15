package com.unicauca.maestria.api.gestionegresados.mappers;

import org.mapstruct.Mapper;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;

@Mapper(componentModel = "spring")
public interface CursoMapper extends GenericMapper<CursoSaveDto, Curso>{
    
}
