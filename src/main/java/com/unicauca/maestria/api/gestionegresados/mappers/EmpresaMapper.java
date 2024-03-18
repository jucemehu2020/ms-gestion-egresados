package com.unicauca.maestria.api.gestionegresados.mappers;

import org.mapstruct.Mapper;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.dtos.EmpresaSaveDto;

@Mapper(componentModel = "spring")
public interface EmpresaMapper extends GenericMapper<EmpresaSaveDto, Empresa>{
    
}
