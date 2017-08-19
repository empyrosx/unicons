/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.intities;

import ru.diman.description.TableInfo;

/**
 *
 * @author Admin
 */
public class EntityImpl implements Entity{

	/**
     * Наименование сущности
     */
	private String name;

	/**
     * Наименование таблицы
     */
	private String tableName;

	/**
     * Набор колонок маппинга
     */
	private TableInfo ti;

    /**
     * Конструктор
     * @param name имя сущности
     * @param tableName имя таблицы
     * @param ti набор колонок
     */
    public EntityImpl(String name, String tableName, TableInfo ti) {
        this.name = name;
        this.tableName = tableName;
        this.ti = ti;
    }

    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }

    public TableInfo getTableInfo() {
        return ti;
    }
}
