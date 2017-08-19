/*
 * TableInfo.java
 *
 * Created on 30 Март 2009 г., 20:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ru.diman.description;

/**
 * Интерфейс для управления раскладкой страницы шаблона формы ввода
 * @author Димитрий
 */
public interface TableInfo extends Iterable<Column> {

    /**
     * Количество колонок
     * @return количество колонок
     */
    public int getCount();

    /**
     * Колонка по индексу
     * @param index
     * @return
     */
    public Column getItem(int index);

    /**
     * Поиск колонки по имени
     * @param columnName имя колонки
     * @return
     */
    public Column FindColumn(String columnName);

    /**
     * Поиск колонки по алиасу
     * @param aliasName алиас колонки
     * @return
     */
    public Column FindColumnByAlias(String aliasName);

    /**
     * получение индекса колонки по имени
     * @param columnName
     * @return индекс колонки в раскладке
     */
    public int IndexOf(String columnName);

    /**
     * получение индекса колонки
     * @param column колонка
     * @return индекс колонки в раскладке
     */
    public int IndexOf(Column column);

    /**
     * Добавление колонки в раскладку
     * @param ci
     */
    public void addColumn(Column ci);

    /**
     * Удаление колонки
     * @param index
     */
    public void removeColumn(int index);

    /**
     * Получение значения параметра
     * @param name имя параметра
     * @return
     */
    public Object getParam(String name);

    /**
     * Установка значения параметра
     * @param name имя параметра
     * @param value значение параметра
     */
    public void setParam(String name, Object value);
}
