/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

/**
 *
 * @author Димитрий
 */
public interface DataManager {

    /**
     * Получение значения колонки
     * @param columnName
     * @return
     */
    public Object getValue(String columnName);

    /**
     * Установка значения колонки
     * @param columnName имя колонки
     * @param value значение
     */
    public void setValue(String columnName, Object value);

    /**
     * Разыменовка 
     * @param code строка разыменовки
     * @param value значение для разыменовки
     * @return
     */
    public Object dereference(String code, Object value);
}
