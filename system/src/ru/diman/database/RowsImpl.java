package ru.diman.database;

import java.util.Iterator;
import ru.diman.description.matrix.DataRow;
import ru.diman.database.QueryManager.Row;
import ru.diman.database.QueryManager.Rows;

/**
 * Класс, представляющий собой реализацию интерфейса Rows для элементов DataRow
 * @author Димитрий
 *
 */
public class RowsImpl implements Rows{

	// набор записей
	private Iterator<DataRow> rows;

	// набор ограничений
	private QueryRestrictions restrictions;

	/**
	 * Конструктор
	 * @param rows набор строк
	 * @param restrictions набор ограничений
	 */
	public RowsImpl(Iterator<DataRow> rows, QueryRestrictions restrictions){
		this.rows = rows;
		this.restrictions = restrictions;
	}

	/**
	 * Проверка наличия записей
	 */
	public boolean hasNext() {
		return rows.hasNext();
	}

	/**
	 * Получение очередной записи
	 */
	public Row next() {
		DataRow dataRow = rows.next();
		return new RowImpl(dataRow, restrictions);
	}

	/**
	 * Удаление записи
	 */
	public void remove() {
		rows.remove();
	}
}
