package ru.diman.database;

import java.util.ArrayList;
import java.util.List;
import ru.diman.intities.Join;
import ru.diman.database.SQLGenerator.SelectedField;

/**
 * Класс, представляющий из себя SQL - запрос
 * @author Димитрий
 *
 */

public class QueryBuilder {

	// основная таблица выборки
	private String mainTable;

	// поля выборки
	private List<SelectedField> selectedFields;

	// подключаемые таблицы
	private List<Join> joins;

	// параметры ограничений
	private List<String> params;

	/**
	 * Конструктор
	 */
	public QueryBuilder(){
		selectedFields = new ArrayList<SelectedField>();
		joins = new ArrayList<Join>();
		params = new ArrayList<String>();
	}

	/**
	 * Установка основной таблицы выборки
	 * @param tableName наименование таблицы
	 */
	public void setMainTable(String tableName){
		this.mainTable = tableName;
	}

	/**
	 * Добавление поля в выборку
	 * @param field
	 */
	public void addField(SelectedField field){
		selectedFields.add(field);
	}

	/**
	 * Добавление join-а
	 * @param join
	 */
	public void addJoin(Join join){
		joins.add(join);
	}

	/**
	 * Добавление набора join-ов
	 * @param join набор join-ов
	 */
	public void addJoins(List<Join> joins){
		this.joins.addAll(joins);
	}

	/**
	 * Добавление ограничения
	 * @param param
	 */
	public void addParam(String param){
		params.add(param);
	}

	/**
	 * Получение запроса выборки
	 */
	public String getPumpSQL(){

		StringBuffer result = new StringBuffer();

		// добавляем поля выборки
		result.append(getSelectSection());

		// добавляем таблицы выборки
		result.append(getFromSection());

		// добавляем ограничения
		result.append(getWhereSection());

		return result.toString();
	}

	/**
	 * Получение секции Select
	 * @return
	 */
	private String getSelectSection() {

		String result = "";
		// добавляем поля
		for (SelectedField field : selectedFields) {

			// перед добавлением поля проверяем поле на корректность
			if (field.isCorrect()){

				// формируем строку выборки поля
				String fieldSelectStr = String.format("%1$s.%2$s %3$s", field.getTableName(), field.getFieldName(), field.getAliasName());

				if (result == ""){
					result = result + fieldSelectStr;
				}
				else{
					result = result + ", " + fieldSelectStr;
				}
			}
		}
		return "select " + result;
	}

	/**
	 * Получение секции From
	 * @return
	 */
	private Object getFromSection() {
		// добавляем таблицу выборки
		String result = " from " + mainTable;

		// добавляем join-ы
		for (Join join : joins) {

			String joinStr = String.format(" left join %1$s on %2$s.%3$s = %4$s.%5$s", join.getForeignTableName(),
					join.getTableName(), join.getKeyFieldName(), join.getForeignTableName(), join.getForeignKey());
			result = result + joinStr;
		}
		return result;
	}

	/**
	 * Получение
	 * @return
	 */
	private Object getWhereSection() {
		String result = "";

		// проверяем есть ли ограничения
		boolean needWhereSection = params.size() > 0;

		// если ограничений нет, то возвращаем пустую строку
		if (!needWhereSection){
			return "";
		}

		// добавляем ограничения
		for (String param : params) {
			if (result == ""){
				result = param;
			}
			else{
				result = result + " AND " + param;
			}
		}
		result = " where " + result;

		return result;
	}

}
