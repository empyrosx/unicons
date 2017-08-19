/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.column.types;

/**
 *
 * @author Admin
 */
public enum HandlerKind {

    /**
     * Специальный обработчик отсутствует
     */
    NONE,

    /**
     * Форматированное значение
     */
    FORMATTED_VALUE,

    /**
     * Справочник
     */
    HAND_BOOK,

    /**
     * Выпадающий список
     */
    PICK_LIST;

    @Override
    public String toString() {

        switch (this){
            case FORMATTED_VALUE: return "Форматированное значение";
            case HAND_BOOK: return "Справочник";
            case PICK_LIST: return "Выпадающий список";
        }
        return "";
    }
}
