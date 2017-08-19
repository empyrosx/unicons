/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.database.ParamsImpl;
import ru.diman.database.Query;
import ru.diman.system.*;
import java.util.Iterator;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.database.QueryManager.Params;

/**
 *
 * @author Admin
 */
public class InsertSQLBuilder {

    /**
     * Раскладка
     */
    private TableInfo ti;

    /**
     * Ограничения
     */
    private QueryRestrictions restrictions;

    /**
     * Конструктор
     * @param ti раскладка
     * @param restrictions ограничения
     */
    public InsertSQLBuilder(TableInfo ti, QueryRestrictions restrictions) {
        this.ti = ti;
        this.restrictions = restrictions;

    }

    public Query generateQuery(){

		DividedText fields = new DividedText();
		DividedText values = new DividedText();

		final ParamsImpl params = new ParamsImpl();

		for (int i = 0; i < ti.getCount(); i++) {

			// получаем очередную колонку
			Column ci = ti.getItem(i);

			if (!ci.getTableName().equalsIgnoreCase(getMainTable(ti))){
				continue;
			}

			if ((ci.getColumnType() == ColumnType.KEY) || (ci.getColumnType() == ColumnType.VALUE)){
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


				if (!param.getTableName().equalsIgnoreCase(getMainTable(ti))){
					continue;
				}

				// добавляем его в список полей на вставку
				fields.add(param.getFieldName());

				values.add("?");
				params.add(param.getFieldName(), ValueType.INTEGER);
			}
		}

		String mainTable = getMainTable(ti);
		final String result = "insert into " + mainTable + " (" + fields.getValues(", ") + ") values (" + values.getValues(", ") + ")";

		return new Query(){

			public Params getParams() {
				return params;
			}

            public String getText() {
                return result;
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

}
