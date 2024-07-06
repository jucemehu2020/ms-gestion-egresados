package com.unicauca.maestria.api.gestionegresados.mappers;

import org.mapstruct.Mapper;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;

@Mapper(componentModel = "spring")
public interface CursoResponseMapper extends GenericMapper<CursosResponseDto, Curso>{
    
}
