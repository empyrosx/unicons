package ru.diman.description.matrix;

import java.util.List;

/**
 * Интерфейс, обеспечивающий доступ к матричным данным
 * @author Димитрий
 *
 */
public interface DataSet {

    /**
     * Число записей
     */
    public int getRowCount();

    /**
     * Число колонок
     */
    public int getColumnCount();

    /**
     * Получение значения
     * @param rowIndex номер строки
     * @param columnIndex номер колонки
     */
    public Object getValue(int rowIndex, int columnIndex);

    /**
     * Получение значения
     * @param rowIndex номер строки
     * @param columnName имя колонки
     */
    public Object getValue(int rowIndex, String columnName);

    /**
     * Установка значения
     * @param rowIndex номер строки
     * @param columnIndex номер колонки
     * @param value значение
     */
    public void setValue(int rowIndex, int columnIndex, Object value);

    /**
     * Установка значения
     * @param rowIndex номер строки
     * @param columnIndex номер колонки
     * @param value значение
     */
    public void setValue(int rowIndex, String columnName, Object value);

    /**
     * Вставка новой строки
     * @param rowIndex номер строки, после которого нужно добавить
     */
    public void addRow(int rowIndex);

    /**
     * Удаление строки
     * @param rowIndex номер удаляемой строки
     */
    public void deleteRow(int rowIndex);

    /**
     * Получение интерфейса для работы с записью
     * @param rowIndex номер строки
     */
    public DataRow getRow(int rowIndex);

    /**
     * Поиск записи по идентификатору
     * @param rowKeys идентификатор
     */
    public int indexOf(DataRow row);

    /**
     * Поиск записи по значениям ключевых полей
     */
    public int findRow(List<Object> keyValues);

    /**
     * Закачать данные
     * @param source источник данных
     */
    public void pumpData(DataSet source);

    /**
     * Очистить данные
     */
    public void clearData();
}
