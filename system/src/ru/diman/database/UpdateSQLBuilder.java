/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.database.DividedText;
import ru.diman.database.ParamsImpl;
import ru.diman.database.Query;
import ru.diman.database.QueryManager.Params;

/**
 *
 * @author Admin
 */
public class UpdateSQLBuilder {

    /**
     * Раскладка
     */
    private TableInfo ti;

    /**
     * Имя ключевого поля
     */
    private String keyFieldName;

    /**
     * Конструктор
     * @param ti раскладка
     * @param keyFieldName имя ключевого поля
     */
    public UpdateSQLBuilder(TableInfo ti, String keyFieldName) {
        this.ti = ti;
        this.keyFieldName = keyFieldName;
    }

    /**
     * Формирование запроса
     * @return
     */
    public Query generateQuery(){

		DividedText values = new DividedText();

		final ParamsImpl params = new ParamsImpl();

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if (!ci.getTableName().equalsIgnoreCase(getMainTable())){
				continue;
			}

            values.add(ci.getAliasName() + " = ?");
            params.add(ci.getName(), ci.getValueType());
		}

        // в параметры добавляем ключевое поле
        params.add(keyFieldName, ValueType.INTEGER);
        
		String mainTable = getMainTable();
		final String result = "update " + mainTable + " set " + values.getValues(", ") + " where " + keyFieldName + " = ?";

		return new Query(){

            public String getText() {
                return result;
            }

            public Params getParams() {
                return params;
            }

		};
    }

	/**
	 * Получение основной таблицы выборки
	 * @param ti
	 * @return
	 */
	private String getMainTable() {
		for (Column ci : ti) {
			if ((!ci.getTableName().isEmpty()) && (!ci.getAliasName().isEmpty())){
				return ci.getTableName();
			}
		}
		return "";
	}
}
