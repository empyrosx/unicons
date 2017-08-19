package ru.diman.database;

import ru.diman.system.*;
import ru.diman.intities.EntityManager;

/**
 * Класс, представлюящий собой ограничение на SQL - запрос
 * @author Димитрий
 *
 */
public class QueryRestriction {

    /**
     * имя ограничения в виде <ИмяCLM>.<ИмяПоля>
     */
	private String name;

	/**
     * значение
     */
	private Object value;

	/**
     * имя таблицы
     */
	private String tableName;

	/**
     * имя поля в таблице
     */
	private String fieldName;

	/**
	 * Конструктор
	 * @param name имя параметра
	 * @param value значение параметра
	 */
	public QueryRestriction(String name, Object value){
        
		this.name = name;
		this.value = value;

        // получаем менеджер сущностей
        EntityManager entityManager = Registry.getEntityManager();

		// получаем полный путь в БД
		String fullDBName = entityManager.getDataBaseName(name);

		int pointPos = fullDBName.indexOf(".");
        
    	this.tableName = fullDBName.substring(0, pointPos);
		this.fieldName = fullDBName.substring(pointPos + 1);
	}

	/**
	 * Получение имени ограничения
     * @return
     */
	public String getName(){
		return name;
	}

	/**
	 * Получение значения ограничения
     * @return
     */
	public Object getValue(){
		return value;
	}

	/**
	 * Получение имени таблицы
     * @return
     */
	public String getTableName(){
		return tableName;
	}

	/**
	 * Получение имени поля
     * @return
     */
	public String getFieldName(){
		return fieldName;
	}
}
