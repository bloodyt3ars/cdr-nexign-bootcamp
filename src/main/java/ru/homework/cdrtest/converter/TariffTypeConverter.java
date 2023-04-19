package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.TariffType;

@Converter(autoApply = true)
public class TariffTypeConverter implements AttributeConverter<TariffType, Integer> {

    /*
    принимает значение типа TariffType и возвращает его порядковый номер,
    увеличенный на единицу, так как enum в Java индексируется с нуля.
     */
    @Override
    public Integer convertToDatabaseColumn(TariffType type) {
        return (type.ordinal()+1);
    }

    /*
    принимает значение типа Integer и возвращает соответствующий элемент из перечисления TariffType,
    полученный из массива элементов с помощью переданного значения, уменьшенного на единицу,
    чтобы скомпенсировать индексацию enum с нуля.
     */
    @Override
    public TariffType convertToEntityAttribute(Integer integer) {
        return TariffType.values()[integer-1];
    }
}
