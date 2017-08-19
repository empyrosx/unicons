package ru.diman.database;

import ru.diman.system.*;
import ru.diman.database.QueryBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.intities.EntityLoader;
import ru.diman.intities.EntityManager;
import ru.diman.intities.EntityManagerImpl;
import ru.diman.intities.Join;
import ru.diman.database.QueryManager.Params;
import ru.diman.database.QueryManager.Query;


/**
 * Класс, представляющий собой генератор SQL - запросов по раскладке
 * @author Димитрий
 *
 */
public class SQLGenerator {

	// менеджер CLM
	private EntityManager clmManager = null;

	// раскладка
	private TableInfo ti;

	// ограничения
	private QueryRestrictions restrictions;


	public SQLGenerator(TableInfo ti, QueryRestrictions restrictions){

		this.clmManager = Registry.getEntityManager();
		this.ti = ti;
		this.restrictions = restrictions;
	}

	/**
	 * Создание запроса по раскладке
	 * @param раскладка
	 * @return
	 */
	public String generateSQL(){

		// создаем запрос
		QueryBuilder query = new QueryBuilder();

		// получаем основную таблицы выборки
		String mainTable = getMainTable();

		// устанавливаем основную таблицу выборки
		query.setMainTable(mainTable);

		// обходим все колонки
		for (int i = 0; i < ti.getCount(); i++) {
			// получаем очередную колонку
			Column ci = ti.getItem(i);

			// генерируем алиас колонки
			String aliasName = "Alias" + Integer.toString(i);

			// создаем поле выборки
			SelectedField selectedField = new SelectedField(ci.getTableName(), ci.getAliasName(), aliasName);

			// добавляем поле выборки в запрос
			query.addField(selectedField);
		}

		// добавляем набор join-ов
		List<Join> joins = getJoinedTables();
		query.addJoins(joins);

		// добавляем ограничения
		if (restrictions != null){

			// получаем итератор
			Iterator<QueryRestriction> i = restrictions.iterator();

			// пока есть ограничения
			while (i.hasNext()){

				// получаем очередное ограничение
				QueryRestriction r = i.next();

				// добавляем ограничение в запрос
				query.addParam("(" + r.getTableName() + "." + r.getFieldName() + " = " + r.getValue() + ")");
			}
		}
		// возвращаем построенный запрос
		return query.getPumpSQL();
	}

	public Query genInsertSQL(){

		DividedText fields = new DividedText();
		DividedText values = new DividedText();

		final ParamsImpl params = new ParamsImpl();

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if (!ci.getTableName().equalsIgnoreCase(getMainTable())){
				continue;
			}

			if ((ci.getColumnType() == ColumnType.KEY) || (ci.getColumnType() == ColumnType.INCREMENT_KEY) || (ci.getColumnType() == ColumnType.VALUE)){
				fields.add(ci.getAliasName());
				values.add("?");
				params.add(ci.getName(), ci.getValueType());
			}
		}

		if (restrictions != null){

			// получаем итератор
			Iterator<QueryRestriction> i = restrictions.iterator();

			// обходим все параметры
			while (i.hasNext()){

				// получаем очередное ограничение
				QueryRestriction param = i.next();


				if (!param.getTableName().equalsIgnoreCase(getMainTable())){
					continue;
				}

				// добавляем его в список полей на вставку
				fields.add(param.getFieldName());

				values.add("?");
				params.add(param.getFieldName(), ValueType.INTEGER);
			}
		}

		String mainTable = getMainTable();
		final String result = "insert into " + mainTable + " (" + fields.getValues(", ") + ") values (" + values.getValues(", ") + ")";

		return new Query(){

			public Params getParams() {
				return params;
			}

			public String getSQLQuery() {
				return result;
			}

		};
	}


	public Query genUpdateSQL(){

		DividedText values = new DividedText();
		DividedText keys = new DividedText();

		final ParamsImpl params = new ParamsImpl();

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if (!ci.getTableName().equalsIgnoreCase(getMainTable())){
				continue;
			}

			if ((ci.getColumnType() == ColumnType.VALUE)){
				values.add(ci.getAliasName() + " = ?");
				params.add(ci.getName(), ci.getValueType());
			}
		}

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if (!ci.getTableName().equalsIgnoreCase(getMainTable())){
				continue;
			}

			if ((ci.getColumnType() == ColumnType.KEY) || (ci.getColumnType() == ColumnType.INCREMENT_KEY)){

				if ((ci.getValueType() == ValueType.STRING)){
					keys.add("(" + ci.getAliasName() + " = ?)");
				}
				else{
					keys.add("(" + ci.getAliasName() + " = ?)");
				}

				//keys.add("?");
				params.add(ci.getName(), ci.getValueType());
			}
		}

		if (restrictions != null){

			// получаем итератор
			Iterator<QueryRestriction> i = restrictions.iterator();

			// обходим все параметры
			while (i.hasNext()){

				// получаем очередное ограничение
				QueryRestriction param = i.next();

				if (!param.getTableName().equalsIgnoreCase(getMainTable())){
					continue;
				}

				// добавляем его в список полей на вставку
				keys.add("(" + param.getFieldName() + " = ?)");
				//keys.add("?");
				params.add(param.getFieldName(), ValueType.INTEGER);
			}
		}

		String mainTable = getMainTable();
		final String result = "update " + mainTable + " set " + values.getValues(", ") + " where " + keys.getValues(" AND ");

		return new Query(){

			public Params getParams() {
				return params;
			}

			public String getSQLQuery() {
				return result;
			}

		};
	}


	public Query genDeleteSQL(){

		DividedText keys = new DividedText();

		final ParamsImpl params = new ParamsImpl();

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if ((ci.getColumnType() == ColumnType.KEY)){
				keys.add("(" + ci.getAliasName() + " = ?)");
				params.add(ci.getName(), ci.getValueType());
			}
		}

		if (restrictions != null){

			// получаем итератор
			Iterator<QueryRestriction> i = restrictions.iterator();

			// обходим все параметры
			while (i.hasNext()){

				// получаем очередное ограничение
				QueryRestriction param = i.next();

				// добавляем его в список полей на вставку
				keys.add("(" + param.getFieldName() + " = ?)");
				params.add(param.getFieldName(), ValueType.INTEGER);
			}
		}

		String mainTable = getMainTable();
		final String result = "delete from " + mainTable + " where " + keys.getValues(" AND ");

		return new Query(){

			public Params getParams() {
				return params;
			}

			public String getSQLQuery() {
				return result;
			}

		};
	}


	/**
	 * Получение набора необходимых join-ов
	 */
	private List<Join> getJoinedTables() {

		List<Join> result = new ArrayList<Join>();

		// набор таблицы выборки
		Set<String> tableNames = getTableNames();

		// получаем основную таблицу выборки
		String mainTable = getMainTable();

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
			List<Join> joins = clmManager.buildJoins(mainTable, tableName);

			for (int j = 0; j < joins.size(); j++) {
				Join join = joins.get(j);
				result.add(join);
			}
		}
		return result;
	}

	/**
	 * Получение набора таблиц, из которых выбираются колонки
	 * @param ti раскладка
	 */
	private Set<String> getTableNames() {
		Set<String> result = new HashSet<String>();

		for (Column ci : ti) {
			if (ci.getTableName() != ""){
				result.add(ci.getTableName());
			}
		}

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
	 * Получение основной таблицы выборки
	 * @param ti
	 * @return
	 */
	private String getMainTable() {
		for (Column ci : ti) {
			if ((ci.getTableName() != "") && (ci.getAliasName() != "")){
				return ci.getTableName();
			}
		}
		return "";
	}

	/**
	 * Класс, представляющий собой описание одного поля выборки
	 * @author Димитрий
	 *
	 */
	public class SelectedField{

		// имя таблицы
		private String tableName;

		// имя поля
		private String fieldName;

		// имя алиаса
		private String aliasName;


		/**
		 * Конструктор
		 * @param tableName имя таблицы
		 * @param fieldName имя поля
		 * @param aliasName имя алиаса
		 */
		public SelectedField(String tableName, String fieldName, String aliasName){
			this.tableName = tableName;
			this.fieldName = fieldName;
			this.aliasName = aliasName;
		}

		/**
		 * Имя таблицы
		 */
		public String getTableName() {
			return tableName;
		}

		/**
		 * Имя поля
		 */
		public String getFieldName() {
			return fieldName;
		}

		/**
		 * Имя алиаса
		 */
		public String getAliasName() {
			return aliasName;
		}

		/**
		 * Проверка на корректность
		 */
		public boolean isCorrect(){
			return (tableName != "") && (fieldName != "");
		}
	}



}
