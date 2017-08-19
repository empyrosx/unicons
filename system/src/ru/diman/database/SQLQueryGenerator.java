/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.database.Query;

/**
 * Интерфейс генератора SQL запросов
 * @author Admin
 */
public interface SQLQueryGenerator {

    /**
     * Получение запроса на выборку данных
     * @return
     */
    public Query getSelectQuery();

    /**
     * Получение запроса на вставку данных
     * @return
     */
    public Query getInsertQuery();

    /**
     * Получение запроса на изменение данных
     * @return
     */
    public Query getUpdateQuery();

    /**
     * Установка имени ключевого поля
     * @param string имя ключевого поля (имя поля в БД)
     */
    public void setKeyField(String string);

}
