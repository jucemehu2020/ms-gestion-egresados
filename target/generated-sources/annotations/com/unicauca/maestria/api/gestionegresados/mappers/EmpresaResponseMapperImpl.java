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
    date = "2024-07-14T03:09:58-0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class EmpresaResponseMapperImpl implements EmpresaResponseMapper {

    @Override
    public Empresa toEntity(EmpresaResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmpresaBuilder empresa = Empresa.builder();

        empresa.id( dto.getId() );
        empresa.nombre( dto.getNombre() );
        empresa.ubicacion( dto.getUbicacion() );
        empresa.cargo( dto.getCargo() );
        empresa.jefeDirecto( dto.getJefeDirecto() );
        empresa.telefono( dto.getTelefono() );
        empresa.correo( dto.getCorreo() );
        empresa.estado( dto.getEstado() );

        return empresa.build();
    }

    @Override
    public EmpresaResponseDto toDto(Empresa entity) {
        if ( entity == null ) {
            return null;
        }

        EmpresaResponseDtoBuilder empresaResponseDto = EmpresaResponseDto.builder();

        empresaResponseDto.id( entity.getId() );
        empresaResponseDto.nombre( entity.getNombre() );
        empresaResponseDto.ubicacion( entity.getUbicacion() );
        empresaResponseDto.cargo( entity.getCargo() );
        empresaResponseDto.jefeDirecto( entity.getJefeDirecto() );
        empresaResponseDto.telefono( entity.getTelefono() );
        empresaResponseDto.correo( entity.getCorreo() );
        empresaResponseDto.estado( entity.getEstado() );

        return empresaResponseDto.build();
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
}
