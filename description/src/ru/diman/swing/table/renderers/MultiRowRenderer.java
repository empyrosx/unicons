package ru.diman.swing.table.renderers;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import ru.diman.description.Column;

/**
 * Рендерер для отображения многострочного текста
 * @author Димитрий
 *
 */
public class MultiRowRenderer implements TableCellRenderer {

    // компонент для отображения
    private JTextArea component;

    /**
     * Конструктор
     * @param column колонка
     */
    public MultiRowRenderer(Column column) {

        // создаем компонент для отображения
        component = new JTextArea();

        // устанавливаем перенос
        component.setLineWrap(true);
        component.setWrapStyleWord(true);
        component.setOpaque(true);

        // устанавливаем размер по умолчанию
        component.setSize(new Dimension(column.getWidth(), 16));
    }

    /**
     * Получение компонента для отображения
     * @return
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        TableColumn tc = table.getColumnModel().getColumn(column);

        // устанавливаем текст
        component.setText(value == null ? "" : value.toString());

        component.setSize(tc.getPreferredWidth(), 16);

        int height = component.getPreferredSize().height;
        int rowHeight = table.getRowHeight(row);
        if ((height > 0) && (height != rowHeight)){
            table.setRowHeight(row, height);
        }

        return component;
    }
}
