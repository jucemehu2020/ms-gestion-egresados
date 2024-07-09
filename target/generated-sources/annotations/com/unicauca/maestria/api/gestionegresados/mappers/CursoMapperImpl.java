package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.domain.Curso.CursoBuilder;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto.CursoSaveDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-09T00:22:33-0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public Curso toEntity(CursoSaveDto dto) {
        if ( dto == null ) {
            return null;
        }

        CursoBuilder curso = Curso.builder();

        curso.fechaFin( dto.getFechaFin() );
        curso.fechaInicio( dto.getFechaInicio() );
        curso.idEstudiante( dto.getIdEstudiante() );
        curso.orientadoA( dto.getOrientadoA() );

        return curso.build();
    }

    @Override
    public CursoSaveDto toDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        CursoSaveDtoBuilder cursoSaveDto = CursoSaveDto.builder();

        cursoSaveDto.fechaFin( entity.getFechaFin() );
        cursoSaveDto.fechaInicio( entity.getFechaInicio() );
        cursoSaveDto.idEstudiante( entity.getIdEstudiante() );
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
