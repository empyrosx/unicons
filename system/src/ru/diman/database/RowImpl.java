package ru.diman.database;

import ru.diman.description.matrix.DataRow;
import ru.diman.database.QueryManager.Row;

/**
 * Класс, представляющий собой реализацию интерфейса Row для DataRow
 * @author Димитрий
 *
 */
public class RowImpl implements Row{

	// запись отчета
	private DataRow row;

	// ограничения
	QueryRestrictions restrictions;

	/**
	 * Конструктор
     * @param row запись отчета
     * @param restrictions
	 */
	public RowImpl(DataRow row, QueryRestrictions restrictions) {
		this.row = row;
		this.restrictions = restrictions;
	}

	public Object getValue(String paramName) {

		// это может быть колонка или параметр
		boolean paramIsColumn = row.indexOf(paramName) > -1;

		// если это колонка, то ...
		if (paramIsColumn){
			// ... получаем её значение
			int columnIndex = row.indexOf(paramName);
			return row.getValue(columnIndex);
		}
		else{
			// ищем такой параметр
			QueryRestriction r = restrictions.find(paramName);
			if (r != null){
				return r.getValue();
			}
		}
		return null;
	}

}
