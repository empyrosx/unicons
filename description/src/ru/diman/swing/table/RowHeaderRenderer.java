package ru.diman.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * Класс-рисовальщик заголовка записи
 * @author Димитрий
 *
 */
public class RowHeaderRenderer implements ListCellRenderer {

    // компонент для отображения
    private JPanel component;
    // таблица
    JTableX table;

    /**
     * Конструктор
     * @param table
     */
    public RowHeaderRenderer(JTableX table) {

        this.table = table;

        // создаем визуализатор
        component = new JPanel();

        // устанавливаем компоновщик
        component.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        // цвет фона
        component.setBackground(Color.lightGray);
    }

    /**
     * Получение компонента для отображения заголовка
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        // удаляем все компоненты с панели
        component.removeAll();

        // получаем набор картинок для нашего заголовка
        //System.out.println(index);

        List<Icon> images = table.getImagesManager().getImages(index);

        JLabel comp = new JLabel(String.valueOf(' '));
        component.add(comp);

        if (images != null) {

            component.setToolTipText("");

            for (int i = 0; i < images.size(); i++) {
                Icon icon = images.get(i);
                comp = new JLabel(icon);
                component.add(comp);
            }

            /*
            if (images.contains(ImageKind.COLUMN_RULE_IMAGE)){
            Icon icon = new ImageIcon("g:\\Templates\\yellow.gif");
            JLabel comp = new JLabel(icon);
            component.add(comp);
            component.setToolTipText("Не выполняется строковое правило");
            };

            if (images.contains(ImageKind.LINE_RULE_IMAGE)){
            Icon icon = new ImageIcon("g:\\Templates\\red.gif");
            component.add(new JLabel(icon));
            component.setToolTipText("Не выполняется линейное правило");
            };
             */

            int rowHeight = table.getRowHeight(index);
            component.setPreferredSize(new Dimension(50, rowHeight));
        }

        return component;
    }
}
