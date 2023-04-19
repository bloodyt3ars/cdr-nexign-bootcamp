package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.CallType;

@Converter(autoApply = true)
public class CallTypeConverter implements AttributeConverter<CallType, Integer> {
    /*
    принимает значение типа CallType и возвращает его порядковый номер,
    увеличенный на единицу, так как enum в Java индексируется с нуля.
     */
    @Override
    public Integer convertToDatabaseColumn(CallType callType) {
        return (callType.ordinal() + 1);
    }

    /*
    принимает значение типа Integer и возвращает соответствующий элемент из перечисления CallType,
    полученный из массива элементов с помощью переданного значения, уменьшенного на единицу,
    чтобы скомпенсировать индексацию enum с нуля.
     */
    @Override
    public CallType convertToEntityAttribute(Integer integer) {
        return CallType.values()[integer - 1];
    }
}
