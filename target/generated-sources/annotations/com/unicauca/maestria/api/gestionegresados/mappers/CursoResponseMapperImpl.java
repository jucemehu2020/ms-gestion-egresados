package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.domain.Curso.CursoBuilder;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto.CursosResponseDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T22:53:51-0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class CursoResponseMapperImpl implements CursoResponseMapper {

    @Override
    public CursosResponseDto toDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        CursosResponseDtoBuilder cursosResponseDto = CursosResponseDto.builder();

        cursosResponseDto.fechaFin( entity.getFechaFin() );
        cursosResponseDto.fechaInicio( entity.getFechaInicio() );
        cursosResponseDto.id( entity.getId() );
        cursosResponseDto.nombre( entity.getNombre() );
        cursosResponseDto.orientadoA( entity.getOrientadoA() );

        return cursosResponseDto.build();
    }

    @Override
    public List<CursosResponseDto> toDtoList(List<Curso> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CursosResponseDto> list = new ArrayList<CursosResponseDto>( entities.size() );
        for ( Curso curso : entities ) {
            list.add( toDto( curso ) );
        }

        return list;
    }

    @Override
    public Curso toEntity(CursosResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        CursoBuilder curso = Curso.builder();

        curso.fechaFin( dto.getFechaFin() );
        curso.fechaInicio( dto.getFechaInicio() );
        curso.id( dto.getId() );
        curso.nombre( dto.getNombre() );
        curso.orientadoA( dto.getOrientadoA() );

        return curso.build();
    }

    @Override
    public List<Curso> toEntityList(List<CursosResponseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Curso> list = new ArrayList<Curso>( dtos.size() );
        for ( CursosResponseDto cursosResponseDto : dtos ) {
            list.add( toEntity( cursosResponseDto ) );
        }

        return list;
    }
}
