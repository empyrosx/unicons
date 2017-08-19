package ru.diman.description.matrix;

import java.util.List;

/**
 * Интерфейс, представляющий одну запись отчета
 * @author Димитрий
 *
 */
public interface DataRow {

    /**
     * Уникальный номер записи
     */
    public int getNo();

    /**
     * Уникальный набор значений ключевых полей
     */
    public List<Object> getKeys();

    /**
     * Получение значения
     * @param columnIndex индекс колонки
     */
    public Object getValue(int columnIndex);

    /**
     * Установка значения
     * @param columnIndex
     * @param columnValue
     */
    public void setValue(int columnIndex, Object columnValue);

    /**
     * Проверка на заполненность полей значений
     */
    public boolean hasValues();

    /**
     * Поиск колонки по имени
     * @param columnName имя колонки
     * @return
     */
    public int indexOf(String columnName);
    
    /**
     * Создать копию
     * @return 
     */
    public DataRow clone();
}
