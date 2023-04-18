package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.CallType;

@Converter(autoApply = true)
public class CallTypeConverter implements AttributeConverter<CallType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(CallType callType) {
        return (callType.ordinal()+1);
    }

    @Override
    public CallType convertToEntityAttribute(Integer integer) {
        return CallType.values()[integer-1];
    }
}
