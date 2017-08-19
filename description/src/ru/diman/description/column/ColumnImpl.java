/*
 * ColumnImpl.java
 *
 * Created on 29 Март 2009 г., 19:23
 *
 * Автор - Зимичев Дмитрий
 */

package ru.diman.description.column;

import ru.diman.description.Column;
import ru.diman.description.column.types.AutoSize;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import javax.swing.SortOrder;
import ru.diman.description.column.types.HandlerKind;

/**
 * Реализация интерфейса Column
 */
public class ColumnImpl extends DBColumnImpl implements Column{

    /**
     * Конструктор
     */
    public ColumnImpl(){
        super();

        // устанавливаем значения по умолчанию
        setVisible(true);
        setWidth(64);
        setColumnClass(Integer.class);
    };

    @Override
    public String getCaption(){
        return getStringValue("Caption");
    }
    
    @Override
    public void setCaption(String value){
        setValue("Caption", value);
    }

    @Override
    public Integer getWidth(){
        return getIntegerValue("Width");
    }
    
    @Override
    public void setWidth(Integer value){
        setValue("Width", value);
    }

    @Override
    public Boolean getVisible(){
        return getBooleanValue("Visible");
    }

    @Override
    public void setVisible(Boolean value){
        setValue("Visible", value);
    }

    @Override
    public Boolean getReadOnly(){
        return getBooleanValue("ReadOnly");
    };

    @Override
    public void setReadOnly(Boolean value){
        setValue("ReadOnly", value);
    };

    @Override
    public ColumnType getColumnType(){
        Object value = getValue("ColumnType");
        return (value == null) ? ColumnType.CALC : (ColumnType) value;
    };

    @Override
    public void setColumnType(ColumnType value){
        setValue("ColumnType", value);
    }

    @Override
    public Integer getLevel(){
        return getIntegerValue("Level");
    }

    @Override
    public void setLevel(Integer value){
        setValue("Level", value);
    }

    @Override
    public String getTextFormat(){
        return getStringValue("TextFormat");
    }

    @Override
    public void setTextFormat(String value){
        setValue("TextFormat", value);
    }

    @Override
    public String getPickList(){
        return getStringValue("PickList");
    }

    @Override
    public void setPickList(String value){
        setValue("PickList", value);
    }

    @Override
    public void setValueType(ValueType value){
        super.setValueType(value);

        if (value == null) {
            return;
        }
        
        switch (value){
            case STRING: setColumnClass(String.class); break;
            case FLOAT: setColumnClass(Float.class); break;
            default:
                setColumnClass(Integer.class);
        }
    }

    @Override
    public Boolean getCollapsed(){
        return getBooleanValue("Collapsed");
    }

    @Override
    public void setCollapsed(Boolean value){
        setValue("Collapsed", value);
    }

    @Override
    public SortOrder getSortOrder(){
        Object value = getValue("SortOrder");
        return (value == null) ? SortOrder.UNSORTED : ( SortOrder) value;
    }
    
    @Override
    public void setSortOrder(SortOrder value){
        setValue("SortOrder", value);
    }

    @Override
    public String getStandardHandBook(){
        return getStringValue("StandartHandBook");
    }

    @Override
    public void setStandardHandBook(String value){
        setValue("StandartHandBook", value);
    }

    @Override
    public Class getColumnClass() {
        return (Class)getValue("Class");
    }

    @Override
    public void setColumnClass(Class columnClass) {
        setValue("Class", columnClass);
    }

    /**
     * Получить вид обработчика
     * @return
     */
    @Override
    public HandlerKind getHandlerKind(){
        Object value = properties.get("HandlerKind");
        return value == null ? HandlerKind.NONE : (HandlerKind) value;
    }

    /**
     * Установить вид обработчика
     * @param value
     */
    @Override
    public void setHandlerKind(HandlerKind value){
        setValue("HandlerKind", value);
    }

    /**
     * Получить информацию об обработчике
     * @return
     */
    @Override
    public String getHandlerInfo(){
        return getStringValue("HandlerInfo");
    }

    /**
     * Установить информацию об обработчике
     * @param value
     */
    @Override
    public void setHandlerInfo(String value){
        setValue("HandlerInfo", value);
    }

    @Override
    public AutoSize getAutoSize() {
        Object value = properties.get("AutoSize");
        return value == null ? AutoSize.AUTO_NONE : (AutoSize) value;
    }

    @Override
    public void setAutoSize(AutoSize value) {
        setValue("AutoSize", value);
    }

    @Override
    public String getScriptOnChange() {
        return getStringValue("ScriptOnChange");
    }

    @Override
    public void setScriptOnChange(String value) {
        setValue("ScriptOnChange", value);
    }
}
