package ru.diman.swing.table.renderers;

import ru.diman.swing.table.*;
import java.text.NumberFormat;
import javax.swing.JComponent;

public class NumberRenderer implements ColumnRenderer {

    // формат
    private NumberFormat numberFormat;

    public NumberRenderer(String format) {

        // создаем формат
        numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setMinimumIntegerDigits(format.length());
    }

    /**
     * Декорирование значения
     */
    public Object renderValue(Object value) {

        if (value != null && (value.toString() != "")) {

            // получаем целое значение
            int intValue = new Integer(value.toString());

            // форматируем
            String formattedValue = numberFormat.format(intValue);

            return formattedValue;
        }
        return null;
    }

    public void prepareRenderer(JComponent renderer) {
    }
}
