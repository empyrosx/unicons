/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.header;

import ru.diman.swing.table.header.ColumnHeader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Класс рисовальщика для отображения заголовков ColumnHeader
 * @author Admin
 */
public class ColumnHeaderRenderer extends DefaultTableCellRenderer {

    static final long serialVersionUID = 0;

    /**
     * Заголовок, который будет отрисовываться этим рисовальщиком
     */
    private ColumnHeader columnHeader;

    /**
     * Конструктор
     * @param columnHeader
     */
    public ColumnHeaderRenderer(ColumnHeader columnHeader) {
        this.columnHeader = columnHeader;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // используем для отображения JTextArea
        // пока только в ней есть Wrap
        JTextArea comp = new JTextArea();

        // устанавливаем шрифт
        Font font = new Font("Arial", 1, 11);
        comp.setFont(font);

        // выводим заголовок
        comp.setText(columnHeader.getTitle());

        // устанавливаем перенос
        comp.setLineWrap(true);
        comp.setWrapStyleWord(true);

        // формируем бордюры
        Border border = BorderFactory.createTitledBorder("");
        comp.setBorder(border);

        // окрашиваем в серый цвет
        comp.setBackground(Color.lightGray);

        // устанавливаем размер по умолчанию
        comp.setSize(new Dimension(columnHeader.getWidthWithNested(), 16));

        // устанавливаем размер ячейки
        Dimension x = comp.getSize();
        x.width = columnHeader.getWidthWithNested();
        comp.setSize(x);

        // запрещаем редактирование
        comp.setEditable(false);

        return comp;
    }
} 