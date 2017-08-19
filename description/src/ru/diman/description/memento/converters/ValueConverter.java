/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

/**
 * Интерфейс для преобразования значений из одного формата в другой и обратно
 * @author Admin
 */
public interface ValueConverter {

    /**
     * Преобразовать значение из входного формата в выходной
     * @param value
     * @return
     */
    public Object convertInputValue(Object value);

    /**
     * Преобразовать значение из выходного формата во входной
     * @param value
     * @return
     */
    public Object convertOutputValue(Object value);
}
