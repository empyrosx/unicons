/*
 * GroupType.java
 *
 * Created on 29 Март 2009 г., 18:26
 *
 * Автор - Зимичев Дмитрий
 */

package ru.diman.description.column.types;

/**
 * Тип агрегатной функции
 */
public enum GroupType {
    /**
     *	Отсутствует
     */
    NONE,
    /**
     * Группировать
     */
    GROUP,
    /**
     * суммировать
     */
    SUM,
    /**
     * последнее
     */
    LAST,
    /**
     * минимум
     */
    MIN,
    /**
     * максимум
     */
    MAX;

    /**
     *
     * @param value
     * @return
     */
    public static GroupType valueOf(int value){
        switch (value){
            case 1:  return GROUP;
            case 2:  return SUM;
            case 3:  return LAST;
            case 4:  return MIN;
            case 5:  return MAX;
            default: return NONE;
        }
    }

    @Override
    public String toString() {

        switch (this){
            case GROUP: return "Группировать";
            case SUM: return "Суммировать";
            case LAST: return "Последнее";
            case MIN: return "Минимум";
            case MAX: return "Максимум";
        }
        return "Не группировать";
    }
}
