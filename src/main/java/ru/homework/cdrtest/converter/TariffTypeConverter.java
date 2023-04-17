package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.TariffType;

@Converter(autoApply = true)
public class TariffTypeConverter implements AttributeConverter<TariffType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TariffType type) {
        return (type.ordinal()+1);
    }

    @Override
    public TariffType convertToEntityAttribute(Integer integer) {
        return TariffType.values()[integer-1];
    }
}
