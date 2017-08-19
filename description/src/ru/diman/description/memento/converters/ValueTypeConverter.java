/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

import ru.diman.description.column.types.ValueType;

/**
 * Преобразователь для свойства колонки "Тип значения"
 * @author Admin
 */
public class ValueTypeConverter implements ValueConverter {

    /**
     * Тип, возвращаемый по умолчанию при возникновении ошибки преобразования или
     * передаче в качестве входного значения null
     */
    private ValueType defaultType;

    /**
     * Конструктор
     */
    public ValueTypeConverter() {
        this(ValueType.INTEGER);
    }

    /**
     * Конструктор
     * @param defaultType тип результата по умолчанию
     */
    public ValueTypeConverter(ValueType defaultType) {
        this.defaultType = defaultType;
    }

    @Override
    public Object convertInputValue(Object value) {

        // если возникнет ошибка, то вернем тип по умолчанию
        ValueType valueType = defaultType;

        try {
            if (value instanceof Integer) {
                valueType = ValueType.valueOf((Integer) value);
                return valueType;

            } else if (value instanceof String) {

                // преобразуем к строке
                String strValue = (String) value;
                if (!strValue.isEmpty()) {

                    Integer intValue = Integer.parseInt(strValue);
                    valueType = ValueType.valueOf(intValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valueType;
    }

    @Override
    public Object convertOutputValue(Object value) {
        
        // null - null
        if (value == null){
            return null;
        }

        // если передан не null, то преобразуем к ValueType
        ValueType valueType = (ValueType) value;
        int intValue = valueType.ordinal();
        return new Integer(intValue).toString();
    }

}
