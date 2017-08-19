/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table.header;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Реализация элемента заголовка с поддержкой вложенных заголовков
 * @author Admin
 */
public class ColumnHeaderImpl implements ColumnHeader{

    /**
     * Колонка, с которой связан этот заголовок
     */
    private TableColumn tc;

    /**
     * Набор вложенных колонок
     */
    private List<ColumnHeader> nestedColumns;

    /**
     * Родительский заголовок
     */
    private ColumnHeader parentHeader;

    /**
     * Признак свернутости заголовка колонки
     */
    private boolean isCollapsed;

    /**
     * Уровень колонки
     */
    private int columnLevel;

    /**
     * Видимость заголовка
     */
    private boolean isVisible;

    /**
     * Поддержка событий при изменении свойств
     */
    protected SwingPropertyChangeSupport changeSupport;

    /**
     * Область вывода данного заголовка
     */
    private Rectangle visibleRect;

    /**
     * Конструктор
     * @param tc колонка
     */
    public ColumnHeaderImpl(TableColumn tc) {
        this.tc = tc;
        this.isVisible = true;
        this.isCollapsed = false;
        this.parentHeader = null;
    }

    /**
     * Добавление вложенного заголовка
     * @param nestedHeader
     */
    public void addNestedHeader(ColumnHeader nestedHeader){

        // если вложенных элементов ещё нет, то создаем набор для них
        if (nestedColumns == null){
            nestedColumns = new ArrayList<ColumnHeader>();
        }

        // если nestedHeader уже существует в наборе, то ничего не делаем
        if (nestedColumns.contains(nestedHeader)){
            return;
        }

        // добавляем заголовок в список вложенных
        nestedColumns.add(nestedHeader);

        // если родитель nestedHeader не совпадает с текущим заголовком, то
        // переназначаем родителя
        if (nestedHeader != this){
            nestedHeader.setParentHeader(this);
        }
    }

    /**
     * Получение текста заголовка
     * @return текст заголовка
     */
    public String getTitle() {
        return (String) tc.getHeaderValue();
    }

    /**
     * Получение набора вложенных заголовков
     * @return
     */
    public Iterator<ColumnHeader> getNestedHeaders() {
        if (nestedColumns != null){
            return nestedColumns.iterator();
        }
        else {
            return null;
        }
    }

    /**
     * Получение ширина заголовка, с учетом вложенных заголовков
     * @return
     */
    public int getWidthWithNested() {
        
        // если есть вложенные заголовки, то возвращаем их суммарную ширину,
        // иначе возвращаем собственную ширину
        if ((nestedColumns != null) && (!isCollapsed())){

            int result = 0;
            
            Iterator<ColumnHeader> nestedHeaders = nestedColumns.iterator();
            while (nestedHeaders.hasNext()){
                ColumnHeader nestedHeader = nestedHeaders.next();
                if (nestedHeader.isVisible()){
                    result = result + nestedHeader.getWidthWithNested();
                }
            }
            return result;
        }
        else {
            int width = tc.getWidth();
            if (width == 0){
                width = tc.getPreferredWidth();
            }
            
            return width;
        }
    }

    /**
     * Получение рисовальщика заголовка
     * @return
     */
    public TableCellRenderer getRenderer() {
        return tc.getHeaderRenderer();
    }

    /**
     * Получение уровня заголовка
     * @return
     */
    public int getLevel() {
        return columnLevel;
    }

    /**
     * Установка уровня заголовка
     */
    public void setLevel(int value) {
        columnLevel = value;
    }

    /**
     * Получение состояние заголовка
     * @return
     */
    public boolean isCollapsed() {
        return isCollapsed;
    }

    /**
     * Установка состояния заголовка
     * @param value состояние, true, если надо свернуть
     */
    public void setCollapsed(boolean value){

        if (isCollapsed != value){
            boolean oldCollapsed = isCollapsed;
            isCollapsed = value;
            firePropertyChange(COLUMN_COLLAPSED_PROPERTY, oldCollapsed, isCollapsed);
        }
    }

    /**
     * Получение видимости колонки
     * @return
     */
    public boolean isVisible() {

        if (parentHeader == null){
            return isVisible;
        }
        else {
            // родительский заголовок должен быть видим и не свернут
            boolean result = isVisible && parentHeader.isVisible() && !parentHeader.isCollapsed();
            return result;
        }
    }

    /**
     * Установка видимости заголовка
     * @param value видимость заголовка
     */
    public void setVisible(boolean value) {
        if (isVisible != value){
            boolean oldVisible = isVisible;
            isVisible = value;
            firePropertyChange(COLUMN_VISIBILITY_PROPERTY, oldVisible, isVisible);
        }
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport != null) {
	    changeSupport.removePropertyChangeListener(listener);
	}
    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (changeSupport != null) {
            changeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    private void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        if (oldValue != newValue) {
            firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Проверка наличия вложенных заголовков
     * @return true, если есть вложенные заголовки
     */
    @Override
    public boolean hasNestedHeaders() {
        return nestedColumns != null;
    }

    /**
     * Получение колонки таблицы, привязанной к данному заголовку
     * @return
     */
    @Override
    public TableColumn getTableColumn() {
        return tc;
    }

    /**
     * Удаление вложенного заголовка
     * @param header
     */
    @Override
    public void removeNestedHeader(ColumnHeader header) {

        // удаляем заголовок из набора вложенных
        if ((nestedColumns != null) && (header != null)){

            // если данного элемента нет в наборе, тогда ничего не делаем
            if (!nestedColumns.contains(header)){
                return;
            }

            // удаляем заголовок из набора
            nestedColumns.remove(header);

            // если в наборе ничего не осталось, то убираем набор
            if (nestedColumns.size() == 0){
                nestedColumns = null;
            }

            // если родитель данного элемента равен текущему, то убираем его
            if (header.getParentHeader() == this) {
                header.setParentHeader(null);
            }
        }
    }

    /**
     * Установка родительского заголовка
     * @param header
     */
    @Override
    public void setParentHeader(ColumnHeader header) {

        // если предки совпадают, то ничего не делаем
        if (parentHeader == header){
            return;
        }

        // если родительский элемент был установлен, тогда удаляем себя из него
        if (parentHeader != null){
            parentHeader.removeNestedHeader(this);
        }

        // устанавливаем новый родительский заголовок
        parentHeader = header;

        // добавляем себя в новый заголовок (если он есть)
        if (parentHeader != null){
            parentHeader.addNestedHeader(this);
        }
    }

    /**
     * Получение родительского заголовка
     * @return
     */
    @Override
    public ColumnHeader getParentHeader() {
        return parentHeader;
    }

    @Override
    public Rectangle getVisibleRect() {
        return visibleRect;
    }

    @Override
    public void setVisibleRect(Rectangle rect) {
        visibleRect = rect;
    }
}
