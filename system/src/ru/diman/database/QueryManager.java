package ru.diman.database;

import java.sql.SQLException;
import java.util.Iterator;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.matrix.DataSet;

/**
 * Интерфейс, обеспечивающий взаимодействие с БД
 * @author Димитрий
 *
 */
public interface QueryManager {

	/**
	 * Загрузка данных
	 * @param query запрос на выборку данных
	 */
	public void loadRows(String query, DataSet rows);

	/**
	 * Выполнение запроса для набора записей
	 * @param query SQL - запрос
	 * @param changedRows набор измененных записей
	 */
	public void updateRows(Query query, Rows changedRows) throws SQLException;

	/**
	 * Интерфейс, представляющий собой одну запись
	 * @author Димитрий
	 *
	 */
	public interface Row{

		/**
		 * Получение значения параметра запроса по имени параметра
		 * @param paramName имя параметра
		 */
		public Object getValue(String paramName);
	}

	/**
	 * Интерфейс, предоставляющий собой набор записей
	 * @author Димитрий
	 *
	 */
	public interface Rows extends Iterator<Row>{

	}

	/**
	 * Интерейс, представляющий собой описание параметров запроса
	 * @author Димитрий
	 *
	 */
	public interface Params {

		/**
		 * Число параметров запроса
		 */
		public int getCount();

		/**
		 * Получение имени параметра по индексу
		 * @param paramIndex
		 */
		public String getName(int paramIndex);

		/**
		 * Получение типа параметра
		 */
		public ValueType getType(int paramIndex);
	}

	/**
	 * Интерфейс, представляющий собой описание запроса с возможными параметрами
	 * @author Димитрий
	 *
	 */
	public interface Query{

		/**
		 * Запрос на вставку
		 */
		public String getSQLQuery();

		/**
		 * Описание параметров запроса
		 */
		public Params getParams();
	}

        public void deleteReportData(int recordIndex);

}
