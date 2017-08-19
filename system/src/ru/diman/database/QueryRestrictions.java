package ru.diman.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, представляющий собор набор ограничений для запроса
 * @author Димитрий
 *
 */
public class QueryRestrictions {

	// набор ограничений
	private List<QueryRestriction> restrictions;

	/**
	 * Конструктор
	 */
	public QueryRestrictions() {
		restrictions = new ArrayList<QueryRestriction>();
	}

	/**
	 * Добавление ограничения
	 * @param restriction ограничение
	 */
	public void add(QueryRestriction restriction){
		restrictions.add(restriction);
	}

	/**
	 * Итератор для получения набора ограничений
	 */
	public Iterator<QueryRestriction> iterator(){
		return restrictions.iterator();
	}

	/**
	 * Поиск ограничения по имени поля
	 * @param fieldName имя поля
	 */
	public QueryRestriction find(String fieldName){

		// ищем ограничение по имени поля в выборке
		for (QueryRestriction r : restrictions) {
			if (r.getFieldName().equals(fieldName)){
				return r;
			}
		}
		return null;
	}

	/**
	 * Получение количества ограничений
	 */
	public int getCount(){
		return restrictions.size();
	}

	/**
	 * Получение ограничения по индексу
	 * @param index инлекс ограничения
	 */
	public QueryRestriction get(int index){
		return restrictions.get(index);
	}
}
