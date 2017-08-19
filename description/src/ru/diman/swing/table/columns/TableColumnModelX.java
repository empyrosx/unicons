/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table.columns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TableColumnModelX{

    /**
     * Набор колонок
     */
    protected List<TableColumnX> columns;

    /**
     * Конструктор
     */
    public TableColumnModelX() {
        columns = new ArrayList<TableColumnX>();
    }

    /**
     * Добавление колонки в модель
     * @param column
     */
    public void addColumn(TableColumnX column) {
        columns.add(column);
    }

    /**
     * Удаление колонки из модели
     * @param column
     */
    public void removeColumn(TableColumnX column) {
        columns.remove(column);
    }

    /**
     * Получение количества колонок
     * @return
     */
    public int getColumnCount() {
        return columns.size();
    }

    /**
     * Получение итератора колонок модели
     * @return
     */
    public Iterator<TableColumnX> getColumns(){
        return columns.iterator();
    }

    /**
     * Получение колонки по индексу
     * @param columnIndex
     * @return
     */
    public TableColumnX getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }
}