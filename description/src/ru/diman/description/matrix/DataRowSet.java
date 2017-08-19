package ru.diman.description.matrix;

import ru.diman.description.Column;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.TableInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, представляющий собой набор записей отчета
 * @author Димитрий
 *
 */
public class DataRowSet implements DataSet{

    // набор записей отчета
    private Map<Integer,DataRowImpl> rows;

    // раскладка
    private TableInfo ti;

    /**
     * Конструктор
     */
    public DataRowSet(TableInfo ti){
        this.rows = new HashMap<Integer,DataRowImpl>();
        this.ti = ti;
    }

    /**
     * Вставка новой строки
     * @param rowIndex номер строки, после которого нужно добавить
     */
    public void addRow(int rowIndex) {
        rows.put(rowIndex, new DataRowImpl(rowIndex, ti, null));
    }

    /**
     * Удаление строки
     * @param rowIndex номер удаляемой строки
     */
    public void deleteRow(int rowIndex) {
        rows.remove(rowIndex);
    }

    /**
     * Число записей
     */
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Получение значения
     * @param rowIndex номер строки
     * @param columnIndex номер колонки
     */
    public Object getValue(int rowIndex, int columnIndex) {

        if (rowIndex < rows.size()){

            DataRowImpl dataRow = rows.get(rowIndex);
            if (dataRow != null){
                return dataRow.getValue(columnIndex);
            }
        }
        return null;
    }

    /**
     * Установка значения
     * @param rowIndex номер строки
     * @param columnIndex номер колонки
     * @param value значение
     */
    public void setValue(int rowIndex, int columnIndex, Object value) {

        if (rowIndex < rows.size()){
            DataRowImpl dataRow = rows.get(rowIndex);
            if (dataRow != null){
                dataRow.setValue(columnIndex, value);
            }
        }
    }

    /**
     * Получение интерфейса для работы с записью
     * @param rowIndex номер строки
     */
    public DataRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    /**
     * Поиск записи по идентификатору
     * @param rowKeys идентификатор
     */
    public int indexOf(DataRow row) {

        // находим ключевые поля
        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < ti.getCount(); i++) {
            if ((ti.getItem(i).getColumnType() == ColumnType.KEY) || (ti.getItem(i).getColumnType() == ColumnType.INCREMENT_KEY)){
                columns.add(ti.getItem(i));
            }
        }

        for (int i = 0; i < rows.size(); i++) {

            DataRow rowData = rows.get(i);

            boolean isEqual = true;

            for (int j = 0; j < columns.size(); j++) {
                Column ci = columns.get(j);
                int columnIndex = ti.IndexOf(ci.getName());
                Object columnValue = row.getValue(columnIndex);
                Object rowColumnValue = rowData.getValue(columnIndex);

                if (rowColumnValue != null){
                    isEqual = isEqual && (rowColumnValue.equals(columnValue));
                } else{
                    isEqual = isEqual && (columnValue == null);
                }
            }

            if (isEqual){
                return i;
            }
        }

        return -1;
    }

    public Object getValue(int rowIndex, String columnName) {
        if (rowIndex < rows.size()){

            DataRowImpl dataRow = rows.get(rowIndex);
            if (dataRow != null){
                return dataRow.getValue(columnName);
            }
        }
        return null;
    }

    public void setValue(int rowIndex, String columnName, Object value) {
        if (rowIndex < rows.size()){

            DataRowImpl dataRow = rows.get(rowIndex);
            if (dataRow != null){
                int columnIndex = ti.IndexOf(columnName);
                if (columnIndex > -1){
                    dataRow.setValue(columnIndex, value);
                }
            }
        }
    }

    /**
     * Поиск записи по значениям ключевых полей
     */
    public int findRow(List<Object> keyValues){

        // находим ключевые поля
        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < ti.getCount(); i++) {
            if (ti.getItem(i).getColumnType() == ColumnType.KEY){
                columns.add(ti.getItem(i));
            }
        }

        // обходим все строки
        for (int i = 0; i < rows.size(); i++) {

            DataRow rowData = rows.get(i);

            boolean isEqual = true;

            for (int j = 0; j < columns.size(); j++) {
                Column ci = columns.get(j);
                int columnIndex = ti.IndexOf(ci.getName());
                Object columnValue = keyValues.get(j);
                Object rowColumnValue = rowData.getValue(columnIndex);

                if (rowColumnValue != null){
                    isEqual = isEqual && (rowColumnValue.equals(columnValue));
                } else{
                    isEqual = isEqual && (columnValue == null);
                }
            }

            if (isEqual){
                return i;
            }
        }

        return -1;

    }

    @Override
    public int getColumnCount() {
        return ti.getCount();
    }

    @Override
    public void pumpData(DataSet source) {

        for (int i = 0; i < source.getRowCount(); i++){
            rows.put(i, (DataRowImpl) source.getRow(i).clone());
        }
    }

    @Override
    public void clearData() {
        rows.clear();
    }
}
