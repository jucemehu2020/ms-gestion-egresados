package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto.CursoSaveDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-25T21:38:56-0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public Curso toEntity(CursoSaveDto dto) {
        if ( dto == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setFechaFin( dto.getFechaFin() );
        curso.setFechaInicio( dto.getFechaInicio() );
        curso.setId( dto.getId() );
        curso.setNombre( dto.getNombre() );
        curso.setOrientadoA( dto.getOrientadoA() );

        return curso;
    }

    @Override
    public CursoSaveDto toDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        CursoSaveDtoBuilder cursoSaveDto = CursoSaveDto.builder();

        cursoSaveDto.fechaFin( entity.getFechaFin() );
        cursoSaveDto.fechaInicio( entity.getFechaInicio() );
        cursoSaveDto.id( entity.getId() );
        cursoSaveDto.nombre( entity.getNombre() );
        cursoSaveDto.orientadoA( entity.getOrientadoA() );

        return cursoSaveDto.build();
    }

    @Override
    public List<Curso> toEntityList(List<CursoSaveDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Curso> list = new ArrayList<Curso>( dtos.size() );
        for ( CursoSaveDto cursoSaveDto : dtos ) {
            list.add( toEntity( cursoSaveDto ) );
        }

        return list;
    }

    @Override
    public List<CursoSaveDto> toDtoList(List<Curso> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CursoSaveDto> list = new ArrayList<CursoSaveDto>( entities.size() );
        for ( Curso curso : entities ) {
            list.add( toDto( curso ) );
        }

        return list;
    }
}
