package ru.diman.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import ru.diman.swing.table.JTableX;
import ru.diman.swing.table.JTableX.TableRenderer;

/**
 * Основной класс рисовальщика. Компонент для отображения получаем у переданного
 * рисовальщика и настраиваем его свойства
 * @author Димитрий
 *
 */
public class StandardRenderer implements TableCellRenderer {

    // рисовальщик
    private final TableCellRenderer renderer;
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    /**
     * Конструктор
     */
    public StandardRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }

    /**
     * Получение компонента для отображения
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // получаем компонент для отображения
        JComponent result = (JComponent) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        result.setOpaque(true);


        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null && !dropLocation.isInsertRow() && !dropLocation.isInsertColumn() && dropLocation.getRow() == row && dropLocation.getColumn() == column) {

            fg = UIManager.getColor("Table.dropCellForeground");
            bg = UIManager.getColor("Table.dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            result.setForeground(fg == null ? table.getSelectionForeground()
                    : fg);
            result.setBackground(bg == null ? table.getSelectionBackground()
                    : bg);
        } else {
            result.setForeground(table.getForeground());
            result.setBackground(table.getBackground());
        }

        result.setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            result.setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    result.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    result.setBackground(col);
                }
            }
        } else {
            result.setBorder(getNoFocusBorder());
        }

        // получаем индекс колонки в модели
        int columnIndexAtModel = table.convertColumnIndexToModel(column);

        // ячейки только для чтения подкрашиваем серым цветом
        if (!table.getModel().isCellEditable(row, columnIndexAtModel)) {
            result.setBackground(Color.LIGHT_GRAY);
        }

        if (table instanceof JTableX){
            JTableX t = (JTableX)table;
            TableRenderer baseRenderer = t.getBaseRenderer();
            if (baseRenderer != null){
                baseRenderer.renderComponent(result, row, column);
            }
        }

        return result;
    }
}
