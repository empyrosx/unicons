package ru.diman.description.matrix;

import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.TableInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, представляющий собой одну запись отчета
 * @author Димитрий
 *
 */
public class DataRowImpl implements DataRow {

    /**
     * Значения полей по индексам
     */
    private Object[] rowValues;

    private int no;
    // набор ключевых атрибутов записи
    //private Columns keys;
    // набор остальных атрибутов записи
    //private Columns values;
    // набор атрибутов записи
    //private HashMap<String, Object> rowValues;
    // раскладка
    private TableInfo ti;

    /**
     * Конструктор
     * @param no
     * @param ti
     * @param columnValues
     */
    public DataRowImpl(int no, TableInfo ti, HashMap<String, Object> columnValues) {
        this.no = no;
        //this.keys = new Columns();
        //this.values = new Columns();
        this.ti = ti;
        //this.rowValues = new HashMap<String, Object>();
        this.rowValues = new Object[ti.getCount()];

        
        // обходим все колонки и формируем набор ключей и значений
        for (int i = 0; i < ti.getCount(); i++){
            rowValues[i] = null;
        }
    }

    /**
     * Получение набора неключевых атрибутов
     */
//    public Iterator<Column> getValues() {
//        return values.getColumns();
//    }

    /**
     * Получение значения
     * @param columnIndex индекс колонки
     */
    public Object getValue(int columnIndex) {
        //ru.diman.description.Column ci = ti.getItem(columnIndex);
        //return rowValues.get(ci.getName());
        return rowValues[columnIndex];
    }

    /**
     * Получение значения
     * @param columnIndex индекс колонки
     */
    public Object getValue(String columnName) {
        /* прикольная реализация :)
        ru.diman.description.Column ci = ti.FindColumn(columnName);
        if (ci != null) {
        return rowValues.get(ci.getName());
        }
        return null;*/
        //return rowValues.get(columnName);
        int columnIndex = ti.IndexOf(columnName);
        if (columnIndex > -1){
            return getValue(columnIndex);
        }
        return null;
    }

    public void setValue(int columnIndex, Object columnValue) {
        //ru.diman.description.Column ci = ti.getItem(columnIndex);

        /*
        Object newValue = columnValue;

        // в зависимости от типа создаем значение
        try {
            if (columnValue != null) {

                // обработка дробных чисел
                if (ci.getValueType().equals(ValueType.FLOAT)) {

                    // если колонка дробная и тип переданного значения не дробный, то преобразуем к дробному
                    if (!columnValue.getClass().equals(Double.class)) {

                        System.out.println("Тип переданного значение не совпадает с типом колонки:");
                        System.out.println("Имя колонки = " + ci.getName());
                        System.out.println("Переданное значение = " + columnValue);
                        System.out.println("Класс значения = " + columnValue.getClass());

                        String sValue = columnValue.toString();
                        if (!sValue.isEmpty()) {
                            newValue = new Double(sValue);
                        } else {
                            newValue = null;
                        }
                    }
                } else if (ci.getValueType().equals(ValueType.INTEGER)) {


                    // если тип колонки целый и тип переданного значение не целый, то
                    // преобразуем к целому
                    if (!columnValue.getClass().equals(Integer.class)) {

                        System.out.println("Тип переданного значения не совпадает с типом колонки:");
                        System.out.println("Имя колонки = " + ci.getName());
                        System.out.println("Переданное значение = " + columnValue);
                        System.out.println("Класс значения = " + columnValue.getClass());

                        String sValue = columnValue.toString();
                        if (!sValue.isEmpty()) {
                            newValue = new Integer(sValue);
                        } else {
                            newValue = null;
                        }
                    }
                } else {
                    newValue = columnValue;
                }
            }

            //columnValues.put(columnName, newValue);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*
        if (ci.getColumnType() == ColumnType.KEY) {
            Iterator<Column> i = keys.getColumns();
            while (i.hasNext()) {
                Column column = i.next();
                if (column.getColumnName().equals(ci.getName())) {
                    column.columnValue = columnValue;
                    break;
                }
            }
        } else {
            if (ci.getColumnType() == ColumnType.VALUE) {
                Iterator<Column> i = values.getColumns();
                while (i.hasNext()) {
                    Column column = i.next();
                    if (column.getColumnName().equals(ci.getName())) {
                        column.columnValue = columnValue;
                        break;
                    }
                }
            }
        }*/

        //rowValues.put(ci.getName(), columnValue);
        rowValues[columnIndex] = columnValue;
    }

    /**
     * Собственная проверка на равенство
     */
    public boolean equals2(Object obj) {
        if (obj instanceof DataRowImpl) {

            // получаем DataRow
            DataRowImpl row = (DataRowImpl) obj;

            // сравниваем по ключам
            List<Object> rowKeys = row.getKeys();
            List<Object> selfKeys = getKeys();

            // обходим все ключи
            for (int i = 0; i < rowKeys.size(); i++) {
                Object rowKeyValue = rowKeys.get(i);
                Object selfKeyValue = selfKeys.get(i);
                if (!rowKeyValue.equals(selfKeyValue)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DataRow clone() {
        DataRow result = new DataRowImpl(no, ti, null);
        for (int i = 0; i < ti.getCount(); i++){
            result.setValue(i, rowValues[i]);
        }
        return result;
    }

    /**
     * Класс, представляющий собой один атрибут записи
     * @author Димитрий
     *
     */
    public class Column {

        // наименование колонки
        private String columnName;
        // значение колонки
        private Object columnValue;

        /**
         * Конструктор
         * @param columnName наименование колонки
         */
        public Column(String columnName, Object columnValue) {
            this.columnName = columnName;
            this.columnValue = columnValue;
        }

        /**
         * Получение наименования колонки
         */
        public String getColumnName() {
            return this.columnName;
        }

        /**
         * Получение значения колонки
         */
        public Object getColumnValue() {
            return this.columnValue;
        }
    }

    /**
     * Класс, представляющий собой набор атрибутов записи
     * @author Димитрий
     *
     */
    public class Columns {

        private List<Column> columns;

        /**
         * Конструктор
         */
        public Columns() {
            this.columns = new ArrayList<Column>();
        }

        /**
         * Добавление колонки
         * @param column колонка
         */
        public void addColumn(Column column) {
            columns.add(column);
        }

        /**
         * Удаление колонки
         * @param column колонка
         */
        public void deleteColumn(Column column) {
            columns.remove(column);
        }

        /**
         * Получение набора ключей
         * @return
         */
        public Iterator<Column> getColumns() {
            return columns.iterator();
        }
    }

    public boolean hasValues() {

        /*
        Iterator<Column> values = getValues();
        while (values.hasNext()) {
            Column column = values.next();
            Object value = column.getColumnValue();
            if (value != null) {
                if (value instanceof Double) {
                    Double d = (Double) value;
                    if (d.floatValue() != 0) {
                        return true;
                    }
                }
            }
        }*/
        return true;
    }

    public int indexOf(String columnName) {
        return ti.IndexOf(columnName);
    }

    public int getNo() {
        return no;
    }

    /**
     * Получение набора значений ключевых полей
     */
    public List<Object> getKeys() {

        // создаем набор
        List<Object> result = new ArrayList<Object>();

        for (int i = 0; i < ti.getCount(); i++){

            ru.diman.description.Column ci = ti.getItem(i);

            // если это ключевое поле
            if (ci.getColumnType() == ColumnType.KEY) {

                // получаем значение
                Object keyValue = rowValues[i];

                // сохраняем в результат
                result.add(keyValue);
            }
        }

        return result;
    }
}
