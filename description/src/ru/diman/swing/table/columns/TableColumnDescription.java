/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table.columns;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Димитрий
 */
public interface TableColumnDescription {

    /**
     * Идентификатор колонки
     * @return
     */
    public String getIdentifier();

    /**
     * Заголовок колонки
     * @return
     */
    public String getHeaderValue();

    /**
     * Ширина колонки
     * @return
     */
    public int getPreferredWidth();

    /**
     * Свернутость заголовка
     * @return 
     */
    public boolean getCollapsed();

    /**
     * Уровень вложенности заголовка
     * @return
     */
    public int getLevel();

    /**
     * Рисовальщик данных данной колонки
     * @return
     */
    public TableCellRenderer getRenderer();

    /**
     * Редактор данных данной колонки
     * @return
     */
    public TableCellEditor getEditor();
}
