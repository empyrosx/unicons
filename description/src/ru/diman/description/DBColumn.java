/*
 * DBColumn.java
 *
 * Created on 30 Март 2009 г., 20:17
 *
 */

package ru.diman.description;

import ru.diman.description.column.types.ValueType;

/**
 * Базовая колонка, представляющая собой колонку базы данных с возможностью задания 
 * имени, для использования в предметной области
 * @author Димитрий
 */
public interface DBColumn {

    /**
     * Получение предметного имени колонки
     * @return
     */
    
    public String getName();

    /**
     * Установка предметного имени колонки
     * @param value новое имя колонки
     */
    public void setName(String value);

    /**
     * Имя таблицы базы данных, к которой относится данная колонка
     * Используется в автоматическом генераторе SQL - запросов
     * @return
     */
    public String getTableName();

    /**
     * Установка таблицы базы данных, к которой относится данная колонка
     * Используется в автоматическом генераторе SQL - запросов
     * @param value
     */
    public void setTableName(String value);

    /**
     * Получение имени колонки в базе данных
     * Используется в автоматическом генераторе SQL - запросов
     * @return
     */
    public String getAliasName();

    /**
     * Установка имени колонки в базе данных
     * Используется в автоматическом генераторе SQL - запросов
     * @param value
     */
    public void setAliasName(String value);

    /**
     * Получение типа поля колонки
     * @return
     */
    public ValueType getValueType();

    /**
     * Установка типа поля колонки
     * @param value
     */
    public void setValueType(ValueType value);

    /**
     * Получение размера значения колонки
     * @return
     */
    public Integer getValueSize();

    /**
     * Установка размера значения колонки
     * @param value
     */
    public void setValueSize(Integer value);

    /**
     * Получение имени внешней таблицы, в случае, если данная колонка является Foreign Key
     * на какую-либо колонку в другой таблице
     * Используется в автоматическом генераторе SQL - запросов
     * @return
     */
    public String getForeignTableName();
    /**
     * Установка имени внешней таблицы, в случае, если данная колонка является Foreign Key
     * на какую-либо колонку в другой таблице
     * Используется в автоматическом генераторе SQL - запросов
     * @param value
     */
    public void setForeignTableName(String value);

    /**
     * Получение имени колонки из внешней таблицы, в случае, если данная колонка является Foreign Key
     * на какую-либо колонку в другой таблице
     * Используется в автоматическом генераторе SQL - запросов
     * @return
     */
    public String getForeignKey();
    
    /**
     * Установка имени колонки из внешней таблицы, в случае, если данная колонка является Foreign Key
     * на какую-либо колонку в другой таблице
     * Используется в автоматическом генераторе SQL - запросов
     * @param value
     */
    public void setForeignKey(String value);
}
