/*
 * Column.java
 *
 * Created on 29 Март 2009 г., 19:16
 *
 * Автор - Зимичев Дмитрий
 */

package ru.diman.description;

import ru.diman.description.column.types.ColumnType;
import javax.swing.SortOrder;
import ru.diman.description.column.types.AutoSize;
import ru.diman.description.column.types.HandlerKind;

/**
 * Интерфейс для описания колонки
 */
public interface Column extends DBColumn{

    /**
     * Получение заголовка колонки
     * @return
     */
    public String getCaption();
    
    /**
     * Установка заголовка колонки
     * @param value
     */
    public void setCaption(String value);

    /**
     * Получение видимости колонки
     * @return
     */
    public Boolean getVisible();
    
    /**
     * Установки видимости колонки
     * @param value
     */
    public void setVisible(Boolean value);

    /**
     * Получение сведений о том, является ли колонка доступной только для чтения
     * @return 
     */
    public Boolean getReadOnly();

    /**
     * Установка колонки в режим только для чтения
     * @param value
     */
    public void setReadOnly(Boolean value);

    /**
     * Тип колонки (назначение колонки в отчете)
     * @return
     */
    public ColumnType getColumnType();
    
    /**
     * Тип колонки (назначение колонки в отчете)
     * @param value
     */
    public void setColumnType(ColumnType value);

    /**
     * Получение ширины колонки
     * @return
     */
    public Integer getWidth();
    
    /**
     * Установка ширины колонки
     * @param value
     */
    public void setWidth(Integer value);

    /**
     * Получение уровня колонки (для построения иерархичных заголовков)
     * @return
     */
    public Integer getLevel();
    
    /**
     * Установка уровня колонки
     * @param value
     */
    public void setLevel(Integer value);

    /**
     * Получение текстового формата
     * @return
     */
    public String getTextFormat();
    
    /**
     * Установка текстового формата
     * @param value
     */
    public void setTextFormat(String value);

    /**
     * Получение выпадающего списка
     * @return
     */
    public String getPickList();

    /**
     * Установка выпадающего списка
     * @param value
     */
    public void setPickList(String value);

    /**
     * Расхлопнутость заголовка колонки
     * @return
     */
    public Boolean getCollapsed();

    /**
     * Расхлопнутость заголовка колонки
     * @param value
     */
    public void setCollapsed(Boolean value);

    /**
     * Сортировка
     * @return
     */
    public SortOrder getSortOrder();

    /**
     * Установка сортировки
     * @param value
     */
    public void setSortOrder(SortOrder value);

    /**
     * Стандартный справочник
     * @return
     */
    public String getStandardHandBook();

    /**
     * Стандартный справочник
     * @param value
     */
    public void setStandardHandBook(String value);

    /**
     * Класс значения колонки (для грида)
     * @return
     */
    public Class getColumnClass();
    
    /**
     * Класс значения колонки (для грида)
     * @param columnClass
     */
    public void setColumnClass(Class columnClass);

    /**
     * Получить вид обработчика
     * @return
     */
    public HandlerKind getHandlerKind();

    /**
     * Установить вид обработчика
     * @param value
     */
    public void setHandlerKind(HandlerKind value);

    /**
     * Получить информацию об обработчике
     * @return
     */
    public String getHandlerInfo();

    /**
     * Установить информацию об обработчике
     * @param value 
     */
    public void setHandlerInfo(String value);

    /**
     * Получить стиль автоматического формата
     * @return
     */
    public AutoSize getAutoSize();

    /**
     * Установить стиль автоматического формата
     * @param value стиль
     */
    public void setAutoSize(AutoSize value);

    /**
     * Получить скрипт на изменение значения
     * @return 
     */
    public String getScriptOnChange();

    /**
     * Установить скрипт на изменение значения
     */
    public void setScriptOnChange(String value);

}