package ru.diman.swing.table.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, представляющий собой данные выпадающего списка
 * @author Димитрий
 *
 */
public class PickList {

    // набор элементов
    List<PickItem> elements;

    /**
     * Создание
     * @param pickList выпадающий список
     * @param useIntegerValues флаг использования целочисленных индексов, если для элемента не указано целое значение
     */
    public PickList(String pickList, boolean useIntegerValues) {
        this.elements = new ArrayList<PickItem>();

        elements.add(new PickItem("", null));

        // получаем набор элементов списка
        String[] values = pickList.split("\n");

        // обходим все элементы
        for (int i = 0; i < values.length; i++) {

            // получаем значение элемента
            String elementValue = values[i];

            // если есть знак "=", значит элемент задан в виде
            // "<ОтображаемоеЗначение>=<РеальноеЗначение>"
            if (elementValue.indexOf("=") > -1) {

                // разделяем значения элемента
                String[] elementValues = elementValue.split("=");

                // добавляем в редактор элемент
                elements.add(new PickItem(elementValues[0], Integer.parseInt(elementValues[1])));
            } else {
                /* Указано только одно отображаемое значение.
                Тут может быть два варианта:
                1. Отображаемый элемент - строка и сохранять надо как строку
                2. Отображаемый элемент - строка и сохранять надо как целое (порядковый номер)
                 */

                if (useIntegerValues) {
                    elements.add(new PickItem(elementValue, Integer.valueOf(i)));
                } else {
                    elements.add(new PickItem(elementValue, elementValue));
                }
            }
        }
    }

    /**
     * Поиск элемент по реальному значению
     * @param value реальное значение элемента
     */
    public Object findItem(Object value) {

        // проходим все элементы списка и ищем нужный
        for (int i = 0; i < elements.size(); i++) {
            PickItem pickItem = elements.get(i);

            if (pickItem.getRealValue() != null) {
                if (pickItem.getRealValue().equals(value)) {
                    return pickItem;
                }
            } else {
                if (value == null) {
                    return pickItem;
                }
            }
        }
        return null;
    }

    /**
     * Получение значения активного элемента
     * @return
     */
    public Object getRealValue(Object element) {
        PickItem pickItem = (PickItem) element;
        if (pickItem != null) {
            return pickItem.getRealValue();
        } else {
            return null;
        }
    }

    /**
     * Получение отображаемого значения по реальному
     * @param value реальное значение
     * @return
     */
    public Object getVisualValue(Object value) {

        // проходим все элементы списка и ищем нужный
        for (int i = 0; i < elements.size(); i++) {
            PickItem pickItem = elements.get(i);
            if (pickItem.getRealValue() != null) {
                if (pickItem.getRealValue().equals(value)) {
                    return pickItem.toString();
                }
            } else {
                if (value == null) {
                    return pickItem.toString();
                }
            }
        }

        return null;
    }

    /**
     * Итератор выпадающего списка
     * @return
     */
    public Iterator<PickItem> getIterator() {
        return elements.iterator();
    }

    /**
     * Класс элемента выпадающего списка
     * @author Димитрий
     *
     */
    class PickItem {

        // отображаемое значение
        private Object visualValue;
        // реальное значение
        private Object realValue;

        /**
         * Создание элемента
         * @param visualValue отображаемое значение
         * @param realValue реальное значение
         */
        PickItem(Object visualValue, Object realValue) {
            this.visualValue = visualValue;
            this.realValue = realValue;
        }

        /**
         * Получение реального значения
         * @return
         */
        Object getRealValue() {
            return realValue;
        }

        /**
         * Получение отображаемого значения
         */
        public String toString() {
            return (String) visualValue;
        }
    }
}
