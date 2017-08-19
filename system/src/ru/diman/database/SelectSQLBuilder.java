/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.database.Query;
import ru.diman.system.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.intities.EntityManager;
import ru.diman.intities.Join;
import ru.diman.database.QueryManager.Params;

/**
 *
 * @author Admin
 */
public class SelectSQLBuilder {

    /**
     * Основная таблица выборки
     */
    private String mainTable;

    /**
     * Набор полей выборки
     */
    private List<SelectedField> fields;

	/**
     * Набор подключаемых таблиц
     */
	private List<Join> joins;

    /**
     * Менеджер сущностей
     */
    private EntityManager entityManager;

    /**
     * Ограничения выборки
     */
    private QueryRestrictions restrictions;

    /**
     * Ключевое поле для идентификации записи
     */
    private String keyFieldName;

    /**
     * Алиас ключевого поля
     */
    private String keyAliasName;

    /**

    /**
     * Конструктор
     * @param ti раскладка
     * @param restrictions
     */
    public SelectSQLBuilder(TableInfo ti, QueryRestrictions restrictions) {
        this(ti, restrictions, "ID");
    }

    /**
     * Конструктор
     * @param ti раскладка
     * @param restrictions ограничения
     * @param keyFieldName имя ключевого поля
     */
    public SelectSQLBuilder(TableInfo ti, QueryRestrictions restrictions, String keyFieldName) {

        // основная таблица выборки
        this.mainTable = getMainTable(ti);

        // поля выборки
        this.fields = getQueryFields(ti);

        // менеджер сущностей
        this.entityManager = Registry.getEntityManager();

        // набор ограничений
        this.restrictions = restrictions;

        // набор соединяемых таблиц
        this.joins = getJoinedTables();

        // по умолчанию ключом будет ID, а алиасом RowKey
        this.keyFieldName = keyFieldName;

        Column keyColumn = ti.FindColumnByAlias(keyFieldName);
        if (keyColumn != null){
            int index = ti.IndexOf(keyColumn);
            this.keyAliasName = "Alias" + String.valueOf(index);
        }
        else {
            this.keyAliasName = "RowKey";
        }
    }

    /**
     * Получение запроса выборки
     * @return
     */
    public Query generateQuery(){

		StringBuffer result = new StringBuffer();

		// добавляем поля выборки
		result.append(getSelectSection());

		// добавляем таблицы выборки
		result.append(getFromSection());

		// добавляем ограничения
		result.append(getWhereSection());

        // получаем весь запрос
		final String query = result.toString();

		// возвращаем построенный запрос
		return new Query(){

            public Params getParams() {
                return null;
            }

            public String getText() {
                return query;
            }

        };
    }

	/**
	 * Получение основной таблицы выборки
	 * @param ti
	 * @return
	 */
	private String getMainTable(TableInfo ti) {
		for (Column ci : ti) {
			if ((!ci.getTableName().isEmpty()) && (!ci.getAliasName().isEmpty())){
				return ci.getTableName();
			}
		}
		return "";
	}

    /**
     * Выделение полей запроса из раскладки
     * @param ti раскладки
     * @return набор полей запроса
     */
    private List<SelectedField> getQueryFields(TableInfo ti) {

        List<SelectedField> result = new ArrayList<SelectedField>();

		// обходим все колонки
		for (int i = 0; i < ti.getCount(); i++) {

            // получаем очередную колонку
			Column ci = ti.getItem(i);

			// генерируем алиас колонки
			String aliasName = "Alias" + Integer.toString(i);

			// создаем поле выборки
			SelectedField selectedField = new SelectedField(ci.getTableName(), ci.getAliasName(), aliasName);

			// добавляем поле выборки в запрос
			result.add(selectedField);
		}

        return result;
    }

	/**
	 * Получение набора таблиц, из которых выбираются колонки
	 * @param ti раскладка
	 */
	private Set<String> getTableNames() {

		Set<String> result = new HashSet<String>();

        // таблицы из полей выборки
		for (SelectedField field : fields) {
			if (field.isCorrect()){
				result.add(field.getTableName());
			}
		}

        // таблицы из ограничений
		if (restrictions != null){

			// получаем итератор
			Iterator<QueryRestriction> i = restrictions.iterator();

			// обходим все ограничения
			while (i.hasNext()){

				// получаем очередное ограничение
				QueryRestriction r = i.next();

				// добавляем таблицу в список
				result.add(r.getTableName());
			}
		}

		return result;
	}


	/**
	 * Получение набора необходимых join-ов
	 */
	private List<Join> getJoinedTables() {

		List<Join> result = new ArrayList<Join>();

		// набор таблицы выборки
		Set<String> tableNames = getTableNames();

		// удаляем из неё основную таблицу выборки
		while (tableNames.contains(mainTable)){
			tableNames.remove(mainTable);
		}

		// обходим все подключаемые таблицы
		Iterator<String> i = tableNames.iterator();
		while (i.hasNext()){

			// получаем очередную таблицу
			String tableName = i.next();

			// пытаемся построить join-ы
			List<Join> joins = entityManager.buildJoins(mainTable, tableName);

			for (int j = 0; j < joins.size(); j++) {
				Join join = joins.get(j);
				result.add(join);
			}
		}
		return result;
	}

	/**
	 * Получение секции Select
	 * @return
	 */
	private String getSelectSection() {

        StringBuffer result = new StringBuffer();

        // ключевое поле всегда должно быть в запросе
		 result.append("select " + mainTable + '.' + keyFieldName + ' ' + keyAliasName);

		// добавляем поля
		for (SelectedField field : fields) {

			// перед добавлением поля проверяем поле на корректность
			if (!field.isCorrect()){ 
                continue;
            }

            // если имя поля совпадает с ключевым, то пропускаем поле
            if (field.getFieldName().equalsIgnoreCase(keyFieldName)){
                continue;
            }

            // формируем строку выборки поля
            String fieldSelectStr = String.format("%1$s.%2$s %3$s", field.getTableName(), field.getFieldName(), field.getAliasName());

            // добавляем поле в выборку
            result.append(", " + fieldSelectStr);
		}
		return result.toString();
	}

	/**
	 * Получение секции From
	 * @return
	 */
	private String getFromSection() {

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
	 * Получение секции ограничений
	 * @return
	 */
	private Object getWhereSection() {

        String result = "";

        if (restrictions == null){
            return result;
        }

		// проверяем есть ли ограничения
		boolean needWhereSection = restrictions.getCount() > 0;

		// если ограничений нет, то возвращаем пустую строку
		if (!needWhereSection){
			return "";
		}

		// добавляем ограничения
		for (int i = 0; i < restrictions.getCount(); i++) {

            QueryRestriction r = restrictions.get(i);

            String param = "(" + r.getTableName() + "." + r.getFieldName() + " = " + r.getValue() + ")";

			if (result.isEmpty()){
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
