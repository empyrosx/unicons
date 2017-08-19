/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * Модель данных, отображающая свойства набора объектов
 * Каждая строка это один объект из списка
 * Каждая колонка это свойство этого объекта
 * @param <E> класс объекта
 * @author Admin
 */
public class PropertiesModel<E> extends AbstractTableModel{

    /**
     * Набор объектов
     */
    private List<E> objects;

    /**
     * Набор свойств
     */
    private List<Property> columns;

    /**
     * Набор свойств
     */
    private PropertyDescriptor[] properties;

    /**
     * Конструктор
     * @param data набор объектов
     * @param elementClass
     */
    public PropertiesModel(Iterator<E> data, Class elementClass) {

        setRows(data);
        columns = PropertyExtractor.getProperties(elementClass);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(elementClass);
            properties = beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException ex) {
            Logger.getLogger(PropertiesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getColumnCount() {
        return columns.size();
        //return properties.length;
    }

    @Override
    public int getRowCount() {
        return objects.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object column = objects.get(rowIndex);


        //PropertyDescriptor p = properties[columnIndex];
        //Method m = p.getReadMethod();

        Property property = columns.get(columnIndex);
        Method m = property.getGetMethod();

        // вызываем метод
        Object result = null;
        try {
            result = m.invoke(column, new Object[]{});
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Property property = columns.get(columnIndex);

        Method m = property.getSetMethod();

        Object column = objects.get(rowIndex);

        if (m != null){
            try {
                Object[] arguments = new Object[]{aValue};
                m.invoke(column, arguments);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * Добавление объекта
     * @param row
     */
    public void addRow(E row){
        objects.add(row);
        fireTableRowsInserted(objects.size(), objects.size());
    }

    /**
     * Удаление объекта
     * @param row
     */
    public void removeRow(E row){
        int index = objects.indexOf(row);
        if (index > -1){
            objects.remove(row);
            fireTableRowsDeleted(index, index);
        }
    }

    /**
     * Поиск колонки в модели
     * @param columnName имя колонки
     * @return индекс колонки в модели или -1, если такой колонки нет
     */
    public int getColumnIndex(String columnName){

        for (int i = 0; i < columns.size(); i++) {
            Property property = columns.get(i);
            if (property.getColumnName().equalsIgnoreCase(columnName)){
                return i;
            }
        }
//        for (int i = 0; i < properties.length; i++) {
//            PropertyDescriptor property = properties[i];
//            if (property.getName().equalsIgnoreCase(columnName)){
//                return i;
//            }
//        }

        return 0;
    }

    /**
     * Получение объекта
     * @param index
     * @return
     */
    public E getRow(int index){
        return objects.get(index);
    }

    /**
     * Установка набора объектов
     * @param rows
     */
    public void setRows(Iterator<E> rows){
        objects = new ArrayList<E>();
        while (rows.hasNext()){
            E object = rows.next();
            objects.add(object);
        }
        fireTableDataChanged();
    }

    /**
     * Получение набора объектов
     * @return
     */
    public Iterator<E> getRows(){
        return objects.iterator();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getGetMethod().getReturnType();
    }


} 