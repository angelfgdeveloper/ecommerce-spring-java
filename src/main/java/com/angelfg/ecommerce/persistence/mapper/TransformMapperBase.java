package com.angelfg.ecommerce.persistence.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface TransformMapperBase<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toListEntity(List<D> listDTO);

    List<D> toListDTO(List<E> listEntity);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}