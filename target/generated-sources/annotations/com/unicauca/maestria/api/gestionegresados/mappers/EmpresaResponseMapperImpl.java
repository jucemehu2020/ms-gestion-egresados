package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa.EmpresaBuilder;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto.EmpresaResponseDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T22:55:15-0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class EmpresaResponseMapperImpl implements EmpresaResponseMapper {

    @Override
    public EmpresaResponseDto toDto(Empresa entity) {
        if ( entity == null ) {
            return null;
        }

        EmpresaResponseDtoBuilder empresaResponseDto = EmpresaResponseDto.builder();

        empresaResponseDto.cargo( entity.getCargo() );
        empresaResponseDto.correo( entity.getCorreo() );
        empresaResponseDto.estado( entity.getEstado() );
        empresaResponseDto.id( entity.getId() );
        empresaResponseDto.jefeDirecto( entity.getJefeDirecto() );
        empresaResponseDto.nombre( entity.getNombre() );
        empresaResponseDto.telefono( entity.getTelefono() );
        empresaResponseDto.ubicacion( entity.getUbicacion() );

        return empresaResponseDto.build();
    }

    @Override
    public List<EmpresaResponseDto> toDtoList(List<Empresa> entities) {
        if ( entities == null ) {
            return null;
        }

        List<EmpresaResponseDto> list = new ArrayList<EmpresaResponseDto>( entities.size() );
        for ( Empresa empresa : entities ) {
            list.add( toDto( empresa ) );
        }

        return list;
    }

    @Override
    public Empresa toEntity(EmpresaResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmpresaBuilder empresa = Empresa.builder();

        empresa.cargo( dto.getCargo() );
        empresa.correo( dto.getCorreo() );
        empresa.estado( dto.getEstado() );
        empresa.id( dto.getId() );
        empresa.jefeDirecto( dto.getJefeDirecto() );
        empresa.nombre( dto.getNombre() );
        empresa.telefono( dto.getTelefono() );
        empresa.ubicacion( dto.getUbicacion() );

        return empresa.build();
    }

    @Override
    public List<Empresa> toEntityList(List<EmpresaResponseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Empresa> list = new ArrayList<Empresa>( dtos.size() );
        for ( EmpresaResponseDto empresaResponseDto : dtos ) {
            list.add( toEntity( empresaResponseDto ) );
        }

        return list;
    }
}
