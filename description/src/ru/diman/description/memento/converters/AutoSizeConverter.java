/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

import ru.diman.description.column.types.AutoSize;
import ru.diman.description.column.types.ValueType;

/**
 * Преобразователь для свойства колонки "Тип значения"
 * @author Admin
 */
public class AutoSizeConverter implements ValueConverter {

    /**
     * Тип, возвращаемый по умолчанию при возникновении ошибки преобразования или
     * передаче в качестве входного значения null
     */
    private AutoSize defaultAutoSize;

    /**
     * Конструктор
     */
    public AutoSizeConverter() {
        this(AutoSize.AUTO_NONE);
    }

    /**
     * Конструктор
     * @param defaultAutoSize стиль по умолчанию
     */
    public AutoSizeConverter(AutoSize defaultAutoSize) {
        this.defaultAutoSize = defaultAutoSize;
    }

    @Override
    public Object convertInputValue(Object value) {

        // если возникнет ошибка, то вернем тип по умолчанию
        AutoSize autoSize = defaultAutoSize;

        try {
            if (value instanceof Integer) {
                autoSize = AutoSize.valueOf((Integer) value);
                return autoSize;

            } else if (value instanceof String) {

                // преобразуем к строке
                String strValue = (String) value;
                if (!strValue.isEmpty()) {

                    Integer intValue = Integer.parseInt(strValue);
                    autoSize = AutoSize.valueOf(intValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return autoSize;
    }

    @Override
    public Object convertOutputValue(Object value) {

        // null - null
        if (value == null){
            return null;
        }

        // если передан не null, то преобразуем к ValueType
        AutoSize autoSize = (AutoSize) value;
        int intValue = autoSize.ordinal();
        return new Integer(intValue).toString();
    }

}
