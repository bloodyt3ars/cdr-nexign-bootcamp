package ru.homework.cdrtest.callrecord;

import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CallRecordMapper {
    CallRecordDTO toDto(CallRecordEntity entity);
}
