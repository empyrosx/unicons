/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.header;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Класс, представляющий элемент заголовка (колонку)
 */
public interface ColumnHeader {

    /**
     * Видимость колонки
     */
    public final static String COLUMN_VISIBILITY_PROPERTY = "columnVisible";

    /**
     * Свернутость колонки
     */
    public final static String COLUMN_COLLAPSED_PROPERTY = "columnCollapsed";

    /**
     * Получение текста заголовка
     * @return текст заголовка колонки
     */
    public String getTitle();

    /**
     * Получение уровня заголовка
     * @return уровень заголовка, 0 - верхний уровень
     */
    public int getLevel();

    /**
     * Установка уровня заголовка
     * @param value уровень
     */
    public void setLevel(int value);

    /**
     * Получение состояния заголовка (свернут или развернут)
     * @return
     */
    public boolean isCollapsed();

    /**
     * Установка состояния заголовка
     * @param value состояние заголовка
     */
    public void setCollapsed(boolean value);

    /**
     * Получение видимости заголовка
     * @return
     */
    public boolean isVisible();

    /**
     * Установка видимости заголовка
     * @param value видимость заголовка
     */
    public void setVisible(boolean value);

    /**
     * Получение рисовальщика
     * @return
     */
    public TableCellRenderer getRenderer();

    /**
     * Получение колонки, с которой связан данный заголовок
     * @return
     */
    public TableColumn getTableColumn();

    
    //////////////////////////////////////////////
    // методы для работы с вложенными колонками //
    //////////////////////////////////////////////


    /**
     * Добавление вложенного заголовка
     * @param header
     */
    public void addNestedHeader(ColumnHeader header);

    /**
     * Удаление вложенного заголовка
     * @param header
     */
    public void removeNestedHeader(ColumnHeader header);

    /**
     * Проверка наличия вложенных заголовков
     * @return true, если вложенные заголовки есть
     */
    public boolean hasNestedHeaders();
    
    /**
     * Получение набора вложенных заголовков
     * @return список вложенных колонок непосредственно следующего уровня
     */
    public Iterator<ColumnHeader> getNestedHeaders();

    /**
     * Получение родительского заголовка
     * @return
     */
    public ColumnHeader getParentHeader();

    /**
     * Установка родительского элемента
     * @param header
     */
    public void setParentHeader(ColumnHeader header);


    //////////////////////////////////////////////
    // методы для работы с размерами колонок    //
    //////////////////////////////////////////////

    /**
     * Получение ширины элемента заголовка
     * @return ширину заголовка, необходимую для его отображения
     */
    public int getWidthWithNested();


    //////////////////////////////////////////////
    // методы для работы со слушателями         //
    //////////////////////////////////////////////
    
    /**
     * Добавление слушателя изменений свойств заголовка
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Удаление слушателя свойств заголовка
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Получение области вывода заголовка
     * @return
     */
    public Rectangle getVisibleRect();

    /**
     * Установка области вывода
     * @param rect
     */
    public void setVisibleRect(Rectangle rect);
}
