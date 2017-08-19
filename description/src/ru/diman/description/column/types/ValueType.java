/*
 * ValueType.java
 *
 * Created on 29 Март 2009 г., 18:28
 *
 * Автор - Зимичев Дмитрий
 */

package ru.diman.description.column.types;

/**
 * Тип значения поля
 */
public enum ValueType {

    /**
     * Целое
     */
    INTEGER,

    /**
     * Строковое
     */
    STRING,

    /**
     * Дробное
     */
    FLOAT;

    /**
     *
     * @param value
     * @return
     */
    public static ValueType valueOf(int value){

        switch (value){
            case 1:
                return STRING;
            case 2:
                return FLOAT;
            default:
                return INTEGER;
        }
    }

    public String toString(){

        switch (this){
            case STRING: return "Строка";
            case FLOAT: return "Дробное";
        }
        return "Целое";

    }
}
