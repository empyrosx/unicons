/*
 * ColumnType.java
 *
 * Created on 29 Март 2009 г., 18:31
 *
 * Автор - Зимичев Дмитрий
 */

package ru.diman.description.column.types;

/**
 * Тип колонки
 **/
public enum ColumnType {
    /**
     * Ключевое поле
     */
    KEY,
    /**
     * Поле счетчик
     */
    INCREMENT_KEY,
    /**
     * Вычислимое поле
     */
    CALC,
    /**
     * Поле ввода
     */
    VALUE,
    /**
     * Колонка заголовка
     */
    CAPTION
}
