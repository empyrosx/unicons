/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table.columns;

import ru.diman.swing.table.header.ColumnHeaderImpl;
import ru.diman.swing.table.header.ColumnHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import ru.diman.swing.table.header.ColumnHeaderRenderer;
import ru.diman.swing.table.renderers.StandardRenderer;

/**
 *
 * @author Admin
 */
public class TableColumnX extends TableColumn{

    /**
     * Заголовок колонки
     */
    private ColumnHeader header;

    /**
     * Конструктор
     * @param modelIndex индекс колонки в модели данных
     * @param description
     */
    public TableColumnX(int modelIndex, TableColumnDescription description) {

        super(modelIndex);
        
        // создаем заголовок
        this.header = new ColumnHeaderImpl(this);
        
        // идентификатор колонки
        this.setIdentifier(description.getIdentifier());

        // текст заголовка
        this.setHeaderValue(description.getHeaderValue());

        // ширина
        this.setPreferredWidth(description.getPreferredWidth());

        // состояние заголовка
        this.getHeader().setCollapsed(description.getCollapsed());

        // уровень заголовка
        this.getHeader().setLevel(description.getLevel());

        // рисовальщик заголовка
        this.setHeaderRenderer(new ColumnHeaderRenderer(getHeader()));

        // рисовальщик данных колонки
        TableCellRenderer r = description.getRenderer();
        if (r != null) {
            this.setCellRenderer(new StandardRenderer(r));
        }

        // редактор данных колонки
        TableCellEditor e = description.getEditor();
        if (e != null) {
            this.setCellEditor(e);
        }
    }

    /**
     * Получение заголовка
     * @return
     */
    public ColumnHeader getHeader(){
        return header;
    }

    /**
     * Установка заголовк
     * @param header
     */
    public void setHeader(ColumnHeader header){
        this.header = header;
    }
} 