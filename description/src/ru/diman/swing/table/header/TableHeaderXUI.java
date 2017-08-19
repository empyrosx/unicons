package ru.diman.swing.table.header;

import ru.diman.swing.table.*;
import ru.diman.swing.table.header.ColumnHeader;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Admin
 */
public class TableHeaderXUI extends BasicTableHeaderUI {

    /**
     * Высоты каждого уровня
     */
    private List<Integer> levelHeights;

    /**
     * Отображение компонента
     */
    @Override
    public void paint(Graphics g, JComponent c) {

        if (header.getColumnModel() == null) {
            return;
        }

        // получаем заголовок
        JTableHeaderX headerX = (JTableHeaderX) header;

        levelHeights = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            levelHeights.add(i, 0);
        }

        // вычисляем высоту каждого уровня
        calcLevelHeights(headerX.getHeaders());

        // выводим колонки верхнего уровня
        paintChildElements(g, new Point(0, 0), 100, headerX.getHeaders());
    }

    /**
     * Вычисление высоты каждого уровня
     * @param headers
     */
    private void calcLevelHeights(Iterator<ColumnHeader> headers) {

        // обходим все элементы заголовка
        while (headers.hasNext()) {

            // получаем очередной заголовок
            ColumnHeader columnHeader = headers.next();

            // если заголовок не видим, то его рассчитывать не надо
            if (columnHeader.isVisible()) {

                // получаем его уровень
                int headerLevel = columnHeader.getLevel();

                // вычисляем его высоту
                int headerHeight = getHeaderHeight(columnHeader);

                // записываем максимальный
                levelHeights.set(headerLevel, Math.max(levelHeights.get(headerLevel), headerHeight));

                // если есть вложенные заголовки, то рассчитываем и их
                if (columnHeader.hasNestedHeaders()) {
                    calcLevelHeights(columnHeader.getNestedHeaders());
                }
            }
        }
    }

    /**
     * Вывод набора элементов. Каждый может включать вложенные элементы
     * @param g
     * @param xRect - Область для вывода
     * @param childs - Набор элементов
     */
    private void paintChildElements(Graphics g, Point leftCorner, int height, Iterator<ColumnHeader> nestedHeaders) {
        if (nestedHeaders != null) {
            while (nestedHeaders.hasNext()) {

                // получаем очередной элемент заголовка
                ColumnHeader nestedHeader = nestedHeaders.next();

                if (nestedHeader.isVisible()) {

                    // выводим элемент
                    paintElement(g, nestedHeader, leftCorner, nestedHeader.getWidthWithNested(), height);
                }
            }
        }
    }

    /**
     * Вывод элемента заголовка
     * @param g
     * @param o - элемент заголовка
     * @param xRect - область вывода
     */
    private void paintElement(Graphics g, ColumnHeader header, Point leftCorner, int width, int height) {

        // получаем размер области вывода для колонки
        int columnHeight = getHeaderHeightWithNested(header);

        // получаем набор вложенных колонок
        Iterator<ColumnHeader> nestedColumns = header.getNestedHeaders();

        // если вложенных колонок нет, то просто выводим данную
        if (nestedColumns == null) {

            int h = this.header.getPreferredSize().height - leftCorner.y;
            paintCell(g, leftCorner.x, leftCorner.y, width, h, header);

            // сдвигаем область вывода влево
            leftCorner.x += width;
        } else {
            // если данный заголовок находится в свернутом положении, то выводим его во всю область
            if (header.isCollapsed()) {

                int h = this.header.getPreferredSize().height - leftCorner.y;
                paintCell(g, leftCorner.x, leftCorner.y, width, h, header);

                // сдвигаем область вывода влево
                leftCorner.x += width;
            } else {
                // выводим данный заголовку сверху
                //columnHeight = getHeaderHeight(header);
                columnHeight = levelHeights.get(header.getLevel());

                paintCell(g, leftCorner.x, leftCorner.y, width, columnHeight, header);

                // вычисляем область для вывода вложенных колонок
                height = height - leftCorner.y - columnHeight;

                // выводим вложенные
                paintChildElements(g, new Point(leftCorner.x, leftCorner.y + columnHeight), height, nestedColumns);

                // сдвигаем область вывода влево
                leftCorner.x += width;
            }

        }
    }

    /**
     * Вывод элемента заголовка
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param column
     */
    private void paintCell(Graphics g, int x, int y, int w, int h, ColumnHeader column) {

        Rectangle clipBounds = g.getClipBounds();
        Rectangle b = new Rectangle(x, y, w, h);
        if (b.intersects(clipBounds)){

            // получаем декоратор колонки
            TableCellRenderer renderer = column.getRenderer();
            if (renderer == null) {
                renderer = header.getDefaultRenderer();
            }

            // получаем индекс колонки в модели
            int columnIndex = column.getTableColumn().getModelIndex();

            // получаем компонент для вывода
            Component component = renderer.getTableCellRendererComponent(
                    header.getTable(), column.getTitle(), false, false, -1, columnIndex);

            // добавляем
            rendererPane.add(component);

            // рисуем
            rendererPane.paintComponent(g, component, header, x, y, w, h, true);

            if (column.getNestedHeaders() != null) {
                Component comp = new JLabel(column.isCollapsed() ? "+" : "-");

                rendererPane.add(comp);
                rendererPane.paintComponent(g, comp, header, x + 2, y + 2, 10, 10, true);
            }

            column.setVisibleRect(new Rectangle(x + 2, y + 2, 10, 10));
        }
    }

    private int getHeaderHeight(ColumnHeader columnHeader) {

        // получаем рисовальщик элемента заголовка
        TableCellRenderer r = columnHeader.getRenderer();

        // если он не задан, используем рисовальщие по умолчанию
        if (r == null) {
            r = header.getDefaultRenderer();
        }

        // получаем индекс колонки в модели
        int columnIndex = columnHeader.getTableColumn().getModelIndex();

        // получаем компонент для отображения
        Component comp = r.getTableCellRendererComponent(header.getTable(), columnHeader.getTitle(), false, false, -1, columnIndex);

        // получаем высоту компонента
        return comp.getPreferredSize().height;
    }

    private int getHeaderHeightWithNested(ColumnHeader columnHeader) {

        // получаем высоту для собственного отображения
        int result = getHeaderHeight(columnHeader);

        // если есть вложенные заголовки и заголовок развернут, то добавляем их высоту
        if (columnHeader.hasNestedHeaders() && !columnHeader.isCollapsed()) {

            // получаем набор вложенных заголовков
            Iterator<ColumnHeader> nestedHeaders = columnHeader.getNestedHeaders();

            // вычисляем максимальную высоту вложенных заголовков
            int maxNestedHeight = 0;
            while (nestedHeaders.hasNext()) {

                // получаем очередной заголовок
                ColumnHeader nestedHeader = nestedHeaders.next();

                // учитываем только видимые заголовки
                if (nestedHeader.isVisible()) {
                    maxNestedHeight = Math.max(maxNestedHeight, getHeaderHeightWithNested(nestedHeader));
                }
            }
            result = result + maxNestedHeight;
        }

        return result;
    }

    /**
     * Получение ширины заголовка
     * @return
     */
    private int getHeaderWidth() {
        int result = 0;
        Iterator<ColumnHeader> i = ((JTableHeaderX) header).getHeaders();
        while (i.hasNext()) {
            ColumnHeader columnHeader = i.next();
            result += columnHeader.getWidthWithNested();
        }
        return result;
    }

    /**
     * Получение высоты заголовка
     * @return
     */
    private int getHeaderHeight() {

        int result = 0;
        Iterator<ColumnHeader> i = ((JTableHeaderX) header).getHeaders();

        if (levelHeights == null) {
            levelHeights = new ArrayList<Integer>();
            for (int j = 0; j < 10; j++) {
                levelHeights.add(j, 0);
            }
            calcLevelHeights(i);
        }

        for (int j = 0; j < 10; j++) {
            result = result + levelHeights.get(j);
        }

        return result;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(getHeaderWidth(), getHeaderHeight());
    }
}
