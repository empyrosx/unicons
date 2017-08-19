/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.header;

import ru.diman.swing.table.header.TableHeaderEvent;
import ru.diman.swing.table.header.TableHeaderListener;
import ru.diman.swing.table.header.ColumnHeader;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Заголовок сетки данных с поддержкой вложенных заголовков
 * В нем должны содержаться только заголовки верхнего уровня
 * @author Admin
 */
public class JTableHeaderX extends JTableHeader implements PropertyChangeListener {

    /**
     * Список заголовков 
     */
    protected List<ColumnHeader> headers;
    /**
     * Набор слушателей
     */
    protected List<TableHeaderListener> listeners;

    /**
     * Конструктор
     * @param cm модель отображаемых колонок
     */
    public JTableHeaderX(TableColumnModel cm) {
        super(cm);

        setReorderingAllowed(false);

        // создаем набор для заголовков верхнего уровня
        headers = new ArrayList<ColumnHeader>();

        // создаем набор слушателей
        listeners = new ArrayList<TableHeaderListener>();


        // добавляем слушателя мыши
        addMouseListener(createMouseListener());
    }

    /**
     * Получение набора заголовков
     * @return набор заголовков
     */
    public Iterator<ColumnHeader> getHeaders() {

        List<ColumnHeader> topHeaders = new ArrayList<ColumnHeader>();

        Iterator<ColumnHeader> i = headers.iterator();
        while (i.hasNext()) {

            ColumnHeader h = i.next();
            if (h.getParentHeader() == null) {
                topHeaders.add(h);
            }
        }

        return topHeaders.iterator();
    }

    /**
     * Добавление элемента заголовка
     * @param header элемент заголовка
     */
    public void addHeader(ColumnHeader header) {
        headers.add(header);
        header.addPropertyChangeListener(this);
    }

    /**
     * Удаление элемента заголовка
     * @param header элемент заголовка
     */
    public void removeHeader(ColumnHeader header) {
        headers.remove(header);
        header.removePropertyChangeListener(this);
    }

    /**
     * Добавление слушателя
     * @param listener слушатель
     */
    public void addListener(TableHeaderListener listener) {
        listeners.add(listener);
    }

    /**
     * Удаление слушателя
     * @param listener слушатель
     */
    public void removeListener(TableHeaderListener listener) {
        listeners.remove(listener);
    }

    /**
     * Событие на изменение свойств
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals(ColumnHeader.COLUMN_VISIBILITY_PROPERTY)) {

            for (int i = 0; i < listeners.size(); i++) {
                TableHeaderListener listener = listeners.get(i);
                listener.columnChangesVisibility(new TableHeaderEvent(evt.getSource()));
            }
        }

        if (evt.getPropertyName().equals(ColumnHeader.COLUMN_COLLAPSED_PROPERTY)) {

            for (int i = 0; i < listeners.size(); i++) {
                TableHeaderListener listener = listeners.get(i);
                listener.columnChangesCollapse(new TableHeaderEvent(evt.getSource()));
            }
        }

        repaint();
    }

    /**
     * обработчик на сворачивание/разворачивание обобщенных колонок
     * @return
     */
    private MouseListener createMouseListener() {
        return new MouseAdapter() {

            /**
             * обработчик на отпускание кнопки мыши
             */
            @Override
            public void mouseReleased(MouseEvent e) {

                // только по левой кнопке мыши
                if (e.getButton() == MouseEvent.BUTTON1) {

                    // получаем колонку в точке клика
                    ColumnHeader columnHeader = getColumnAt(e.getPoint());

                    // если есть заголовок в этом месте, то
                    if (columnHeader != null) {

                        // меняем состояние заголовка
                        columnHeader.setCollapsed(!columnHeader.isCollapsed());
                    }
                }

                // обработка предка
                super.mouseReleased(e);
            }
        };
    }

    /**
     * получить колонку в данной точке
     *
     * @param p
     * @return 
     */
    public ColumnHeader getColumnAt(Point p) {

        ColumnHeader result = null;

        for (int i = 0; i <= headers.size() - 1; i++) {
            ColumnHeader tc = headers.get(i);

            result = getColumnAt(tc, p);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private ColumnHeader getColumnAt(ColumnHeader tc, Point p) {

        // получаем прямоугольник вывода
        Rectangle r = tc.getVisibleRect();

        // если он содержит нашу точку, то выходим
        if (r.contains(p) && tc.isVisible()) {
            return tc;
        }
        return null;
    }
}
