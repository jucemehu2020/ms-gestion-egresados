package com.unicauca.maestria.api.gestionegresados.mappers;

import org.mapstruct.Mapper;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;

@Mapper(componentModel = "spring")
public interface EmpresaResponseMapper extends GenericMapper<EmpresaResponseDto, Empresa>{
    
}
