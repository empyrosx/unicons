/*
 * TableInfoImpl.java
 *
 * Created on 30 Март 2009 г., 20:38
 *
 * Автор - Зимичев Дмитрий
 */
package ru.diman.description.tableinfo;

import ru.diman.description.TableInfo;
import ru.diman.description.Column;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Реализация интерфейса TableInfo
 * @author Димитрий
 */
public class TableInfoImpl implements TableInfo {

    /**
     * Набор колонок отчета
     */
    private List<Column> columns;

    /**
     * Параметры раскладки
     */
    private Map<String,Object> params;

    /**
     * Индексы колонок по именам
     */
    private Map<String,Integer> columnIndexes;

    /**
     * Конструктор
     */
    public TableInfoImpl() {

        // набор колонок
        columns = new ArrayList<Column>();

        // набор параметров
        params = new HashMap<String, Object>();

        // индексы колонок по именам
        columnIndexes = new HashMap<String, Integer>();
    }

    /**
     * Получение количества колонок
     * @return количество колонок
     */
    @Override
    public int getCount() {
        return columns.size();
    }

    /**
     * Колонка по индексу
     * @param index
     */
    @Override
    public Column getItem(int index) {
        return columns.get(index);
    }

    /**
     * Поиск колонки по имени
     * @param columnName имя колонки
     */
    @Override
    public Column FindColumn(String columnName) {
        int index = IndexOf(columnName);
        if (index > -1) {
            return getItem(index);
        }
        ;
        return null;
    }

    /**
     * Поиск колонки по алиасу
     * @param aliasName алиас колонки
     */
    @Override
    public Column FindColumnByAlias(String aliasName) {

        Column result = null;

        // цикл по колонкам
        for (int i = 0; i <= getCount() - 1; i++) {
            Column ci = getItem(i);
            if (ci.getAliasName().equalsIgnoreCase(aliasName)) {
                result = ci;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<Column> iterator() {
        return new tiIterator();
    }

    /**
     * получение индекса колонки по имени
     * @return индекс колонки в раскладке
     */
    @Override
    public int IndexOf(String columnName) {

        // возможно уже искали
        if (columnIndexes.containsKey(columnName)){
            return columnIndexes.get(columnName);
        }
        else {

            // выполням поиск
            for (int i = 0; i <= getCount() - 1; i++) {

                // получаем очередную колонку
                Column ci = getItem(i);

                // если наша, то ...
                if (ci.getName().equals(columnName)) {
                    columnIndexes.put(columnName, i);
                    return i;
                }
            }
        }

        // если не нашли, то возвращем -1
        return -1;
    }

    /**
     * получение индекса колонки по имени
     * @return индекс колонки в раскладке
     */
    @Override
    public int IndexOf(Column column) {

        int result = -1;

        // цикл по колонкам
        for (int i = 0; i <= getCount() - 1; i++) {
            Column ci = getItem(i);
            if (ci.equals(column)) {
                result = i;
                break;
            }
        }

        return result;
    }

    /**
     * Добавление колонки в раскладку
     * @param ci
     */
    @Override
    public void addColumn(Column ci) {
        columns.add(ci);
    }

    /**
     * Удаление колонки
     * @param index
     */
    @Override
    public void removeColumn(int index) {
        columns.remove(index);
    }

    @Override
    public Object getParam(String name) {
        return params.get(name);
    }

    @Override
    public void setParam(String name, Object value) {
        params.put(name, value);
    }

    /**
     * Внутренний класс - реализация итератора
     */
    class tiIterator implements Iterator<Column> {

        /**
         * Курсор
         */
        int cursor = 0;

        /**
         * Флаг наличия элементов
         * @return true, если есть ещё элементы
         */
        @Override
        public boolean hasNext() {
            return cursor != getCount();
        }

        /**
         * Получение очередного элемента итератора
         * @return очередной элемент списка
         * @exception NoSuchElementException в случае отсутствия элементов всписке
         */
        @Override
        public Column next() {
            try {
                Column next = getItem(cursor);
                cursor++;
                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new NoSuchMethodError("Метод не поддерживается");
        }

        ;
    };
}
