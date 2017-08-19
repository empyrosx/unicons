/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.intities;

import java.util.List;
import ru.diman.description.TableInfo;

/**
 * Менеджер сущностей
 * @author Admin
 */
public interface EntityManager {

    /**
     * Получение количества сущностей
     * @return
     */
    public int getCount();

    /**
     * Получение сущности
     * @param index индекс сущности
     * @return
     */
    public Entity getItem(int index);


    /**
     * Поиск сущности по имени
     * @param name
     * @return
     */
    public Entity findByName(String name);

    /**
     * Получение полного имени из БД
     * @param columnIdent идентификатор поля сущности из предметной области
     * @return возвращает имя таблицы + '.' + имя поля
     */
	public String getDataBaseName(String columnIdent);

    /**
     *
     * @param firstTableName
     * @param secondTableName
     * @return
     */
	public List<Join> buildJoins(String firstTableName, String secondTableName);

	/**
	 * Создание раскладки по именам полей из CLM
     * @param columnList 
     * @return
	 */
	public TableInfo createTableInfo(String columnList);

}
