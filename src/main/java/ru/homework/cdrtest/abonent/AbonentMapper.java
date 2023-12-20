package ru.homework.cdrtest.abonent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AbonentMapper {

    @Mapping(target = "tariffType", ignore = true)
    AbonentEntity toEntity(AbonentCreateDTO dto);

    AbonentDTO toDto(AbonentEntity entity);

    AbonentShortDTO toShortDto(AbonentEntity entity);
}
