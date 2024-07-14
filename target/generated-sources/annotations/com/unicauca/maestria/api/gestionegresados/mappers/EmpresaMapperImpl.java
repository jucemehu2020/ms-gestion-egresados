package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa.EmpresaBuilder;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto.EmpresaSaveDtoBuilder;
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
public class EmpresaMapperImpl implements EmpresaMapper {

    @Override
    public Empresa toEntity(EmpresaSaveDto dto) {
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
        empresa.idEstudiante( dto.getIdEstudiante() );

        return empresa.build();
    }

    @Override
    public EmpresaSaveDto toDto(Empresa entity) {
        if ( entity == null ) {
            return null;
        }

        EmpresaSaveDtoBuilder empresaSaveDto = EmpresaSaveDto.builder();

        empresaSaveDto.id( entity.getId() );
        empresaSaveDto.idEstudiante( entity.getIdEstudiante() );
        empresaSaveDto.nombre( entity.getNombre() );
        empresaSaveDto.ubicacion( entity.getUbicacion() );
        empresaSaveDto.cargo( entity.getCargo() );
        empresaSaveDto.jefeDirecto( entity.getJefeDirecto() );
        empresaSaveDto.telefono( entity.getTelefono() );
        empresaSaveDto.correo( entity.getCorreo() );
        empresaSaveDto.estado( entity.getEstado() );

        return empresaSaveDto.build();
    }

    @Override
    public List<Empresa> toEntityList(List<EmpresaSaveDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Empresa> list = new ArrayList<Empresa>( dtos.size() );
        for ( EmpresaSaveDto empresaSaveDto : dtos ) {
            list.add( toEntity( empresaSaveDto ) );
        }

        return list;
    }

    @Override
    public List<EmpresaSaveDto> toDtoList(List<Empresa> entities) {
        if ( entities == null ) {
            return null;
        }

        List<EmpresaSaveDto> list = new ArrayList<EmpresaSaveDto>( entities.size() );
        for ( Empresa empresa : entities ) {
            list.add( toDto( empresa ) );
        }

        return list;
    }
}
