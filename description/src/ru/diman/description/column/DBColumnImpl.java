/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.column;

import ru.diman.description.DBColumn;
import ru.diman.description.column.types.ValueType;
import java.util.Dictionary;
import java.util.Properties;

/**
 * Базовая реализация колонки базы данных
 * @author Admin
 */
public class DBColumnImpl implements DBColumn{

    /**
     * Имя колонки (вынесено отдельным свойство для ускорения)
     */
    private String name;

    /**
     * Коллекция значений атрибутов колонки
     */
    protected Dictionary<Object, Object> properties;

    /**
     * Конструктор
     */
    public DBColumnImpl() {
         this.properties = new Properties();
         setTableName("utypedformsdetail");
    }

    /**
     * Получение значения свойства
     * @param property имя свойства
     * @return
     */
    protected Object getValue(String property){
        return properties.get(property);
    };

    /**
     * Получение значения строкового свойства
     * @param property имя свойства
     * @return значение свойства, если оно не null, "" в противном случае
     */
    protected String getStringValue(String property){
        Object value = properties.get(property);
        return value == null ? "" : (String) value;
    }

    /**
     * Получение значения целого свойства
     * @param property имя свойства
     * @return значение свойства, если оно не null, 0 в противном случае
     */
    protected Integer getIntegerValue(String property){
        Object value = properties.get(property);
        return value == null ? 0 : (Integer) value;
    }

    /**
     * Получение значения булева свойства
     * @param property имя свойства
     * @return значение свойства, если оно не null, false в противном случае
     */
    protected Boolean getBooleanValue(String property){
        Object value = properties.get(property);
        return value == null ? Boolean.FALSE : (Boolean) value;
    }

    /**
     * Установка значения свойства
     * @param property имя свойства
     * @param value значение
     */
    protected void setValue(String property, Object value){
        if (value == null){
            properties.remove(property);
        }
        else {
            properties.put(property, value);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name = value;
    }

    @Override
    public String getTableName() {
        return getStringValue("TableName");
    }

    @Override
    public void setTableName(String value) {
        if (!value.isEmpty()){
            setValue("TableName", value.toUpperCase());
        }
    }

    @Override
    public String getAliasName() {
        return getStringValue("AliasName");
    }

    @Override
    public void setAliasName(String value) {
        setValue("AliasName", value.toUpperCase());
    }

    @Override
    public ValueType getValueType() {
        Object value = properties.get("ValueType");
        return value == null ? ValueType.INTEGER : (ValueType) value;
    }

    @Override
    public void setValueType(ValueType value) {
        setValue("ValueType", value);
    }

    @Override
    public Integer getValueSize() {
        return getIntegerValue("ValueSize");
    }

    @Override
    public void setValueSize(Integer value) {
        setValue("ValueSize", value);
    }

    @Override
    public String getForeignTableName() {
        return getStringValue("ForeignTableName");
    }

    @Override
    public void setForeignTableName(String value) {
        setValue("ForeignTableName", value.toUpperCase());
    }

    @Override
    public String getForeignKey() {
        return getStringValue("ForeignKey");
    }

    @Override
    public void setForeignKey(String value) {
        setValue("ForeignKey", value.toUpperCase());
    }
}
