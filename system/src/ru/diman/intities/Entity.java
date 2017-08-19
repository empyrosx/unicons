/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.intities;

import ru.diman.description.TableInfo;

/**
 * Интерфейс, представляющий собой маппинг сущности предметной области на таблицу базы данных
 * @author Admin
 */
public interface Entity {

    /**
     * Получение наименование сущности
     * @return
     */
    public String getName();

    /**
     * Получение наименования таблицы базы данных
     * @return
     */
    public String getTableName();

    /**
     * Получение набора колонок маппинга
     * @return
     */
    public TableInfo getTableInfo();
}
