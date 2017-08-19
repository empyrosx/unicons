/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.impl;

import ru.diman.description.memento.converters.ValueConverter;
import java.lang.reflect.Method;

/**
 *
 * @author Admin
 */
public class PropertyMapping {

        /**
         * Имя свойства колонки
         */
        private String propertyName;

        /**
         * Имя узла в XML документе
         */
        private String columnNode;

        /**
         * Тип значения
         */
        private Class clazz;

        /**
         * Конвертер значений
         */
        private ValueConverter converter;

        /**
         * Получение преобразователя значения
         * @return
         */
        public ValueConverter getConverter() {
            return converter;
        }

        /**
         * Установка преобразователя
         * @param converter
         */
        public void setConverter(ValueConverter converter) {
            this.converter = converter;
        }

        /**
         * Получение типа свойства
         * @return
         */
        public Class getClazz() {
            return clazz;
        }

        /**
         * Установка типа свойства
         * @param clazz
         */
        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        /**
         * Получение имени узла, в котором хранится данное свойство
         * @return
         */
        public String getColumnNode() {
            return columnNode;
        }

        /**
         * Установка имени узла, в котором хранится данное свойство
         * @param columnNode
         */
        public void setColumnNode(String columnNode) {
            this.columnNode = columnNode;
        }

        /**
         * Получение имени свойства
         * @return
         */
        public String getPropertyName() {
            return propertyName;
        }

        /**
         * Установка имени свойства
         * @param propertyName
         */
        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        /**
         * Установка значения с предварительной прогонкой через преобразователь
         * @param object объект, значение свойства которого надо установить
         * @param value значение для установки
         */
        public void setValue(Object object, Object value){

            Object propertyValue = value;
            
            // если задан преобразователь, то пропускаем значение через него
            if (getConverter() != null) {
                propertyValue = getConverter().convertInputValue(propertyValue);
            } else {
                if (getClazz().equals(Integer.class)) {
                    String strValue = (String) propertyValue;
                    if (!strValue.isEmpty()){
                        propertyValue = Integer.valueOf(strValue);
                    }
                    else {
                        propertyValue = null;
                    }
                }
            }

            Class[] types = new Class[]{getClazz()};
            try {
                Method m = object.getClass().getMethod("set" + propertyName, types);
                m.invoke(object, new Object[]{propertyValue});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Получение значения с последующей прогонкой через преобразователь
         * @param object объект, значение свойства которого надо установить
         * @param value значение для установки
         */
        public Object getValue(Object object){

            Object result = "";

            Class[] types = new Class[]{};
            try {
                Method m = object.getClass().getMethod("get" + propertyName, types);
                result = m.invoke(object, new Object[]{});
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // если задан преобразователь, то пропускаем значение через него
            if (getConverter() != null) {
                result = getConverter().convertOutputValue(result);
            } else {
                /*
                if (getClazz().equals(Integer.class)) {
                    String strValue = (String) result;
                    if (!strValue.isEmpty()){
                        result = Integer.valueOf(strValue);
                    }
                    else {
                        result = null;
                    }
                }
                 */
            }

            return result;
        }

        /**
         * Конструктор
         * @param propertyName имя свойства колонки
         * @param columnNode имя узла в XML
         */
        public PropertyMapping(String propertyName, String columnNode) {
            this(propertyName, columnNode, String.class);
        }

        /**
         * Конструктор
         * @param propertyName имя свойства колонки
         * @param columnNode имя узла в XML
         * @param clazz тип значения
         */
        public PropertyMapping(String propertyName, String columnNode, Class clazz) {
            this.propertyName = propertyName;
            this.columnNode = columnNode;
            this.clazz = clazz;
        }

        /**
         * Конструктор
         * @param propertyName имя свойства колонки
         * @param columnNode имя узла в XML
         * @param clazz тип значения
         * @param converter преобразователь
         */
        public PropertyMapping(String propertyName, String columnNode, Class clazz, ValueConverter converter) {
            this(propertyName, columnNode, clazz);
            this.converter = converter;
        }
}
