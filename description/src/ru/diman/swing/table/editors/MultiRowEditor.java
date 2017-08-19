/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.editors;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author Димитрий
 */
public class MultiRowEditor extends AbstractCellEditor implements TableCellEditor {

    /**
     * Компонент для редактирования многострочного текста
     */
    private JTextArea component;

    /**
     * Сетка данных, для колонок которой подвешен данный редактор
     */
    private JTable table;

    /**
     * Конструктор
     */
    public MultiRowEditor() {

        // создаем компонент для редактирования
        component = new JTextArea();

        // устанавливаем перенос
        component.setLineWrap(true);
        component.setWrapStyleWord(true);
        component.setOpaque(true);

        // оригинальный бордюр!
        component.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));

        // добавляем слушателя клавиатуры для
        // динамического изменения высоты строки при изменении данных ячейки
        component.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume();
                    table.getCellEditor().stopCellEditing();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                // получаем оптимальную высоту для отражения многострочного
                // текста данной ячейки
                int height = component.getPreferredSize().height;

                // получаем редактируемую строку
                int editingRow = table.getEditingRow();

                // устанавливаем новую высоту строки
                if ((editingRow != -1) & (height > 0)) {
                    table.setRowHeight(editingRow, height);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return component.getText();
    }

    /**
     * Обработчик перекрываем для того, чтобы ячейка не начинала редактироваться
     * от простого одинарного клика по ней
     * @param anEvent
     * @return
     */
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent) anEvent).getClickCount() >= 2;
        }
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, int column) {

        this.table = table;

        TableColumn tc = table.getColumnModel().getColumn(column);
        component.setSize(tc.getWidth(), component.getSize().height);

        if (value != null) {
            component.setText((String) value);
        } else {
            component.setText("");
        }

        return component;
    }
}
