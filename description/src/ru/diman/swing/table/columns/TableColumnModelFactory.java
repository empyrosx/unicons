/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.columns;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;

/**
 * Фабрика создания модели колонок для сетки данных JTable
 * @author Admin
 */
public class TableColumnModelFactory {

    public static TableColumnModelX createColumnModel(TableInfo ti) {

        // создаем модель колонок данной таблицы
        TableColumnModelX result = new TableColumnModelX();
        for (int i = 0; i < ti.getCount(); i++) {
            Column column = ti.getItem(i);
            if (column.getVisible()) {

                // создаем колонку
                TableColumnX tc = new TableColumnX(i, new DefaultTableColumnDescription(column));

                // добавляем в набор
                result.addColumn(tc);
            }
        }
        return result;
    }
}
