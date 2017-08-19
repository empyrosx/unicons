package ru.diman.system;

import ru.diman.database.SQLGenerator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataRow;
import ru.diman.description.matrix.DataSet;
import ru.diman.database.QueryManager;
import ru.diman.database.QueryManager.Query;
import ru.diman.database.QueryManager.Rows;
import ru.diman.database.QueryManagerImpl;
import ru.diman.database.RowsImpl;
import ru.diman.database.QueryRestrictions;

public class DataSaver {

    private QueryManager qm;

    public DataSaver() {
        qm = new QueryManagerImpl();
    }

    /**
     * Сохранить изменения в БД
     * @param sourceData исходные данные
     * @param editedData отредактированные данные
     * @param ti
     * @param restrictions
     */
    public void saveData(DataSet sourceData, DataSet editedData, TableInfo ti, QueryRestrictions restrictions) {

        final List<DataRow> insertedRecords = new ArrayList<DataRow>();
        final List<DataRow> updatedRecords = new ArrayList<DataRow>();
        final List<DataRow> deletedRecords = new ArrayList<DataRow>();

        // обходим исходные данные
        for (int i = 0; i < sourceData.getRowCount(); i++) {

            // получаем исходную запись
            DataRow sourceDataRow = sourceData.getRow(i);

            // если в отредактированном наборе этой записи нет, то формируем запрос на удаление
            if (editedData.indexOf(sourceDataRow) < 0) {
                /*
                 * Добавляем запись в набор на удаление
                 */
                deletedRecords.add(sourceDataRow);
            }
        }

        // обходим отредактированные данные
        for (int i = 0; i < editedData.getRowCount(); i++) {

            // получаем исходную запись
            DataRow editedDataRow = editedData.getRow(i);

            // если в исходном наборе этой записи нет, то формируем запрос на вставку
            if (sourceData.indexOf(editedDataRow) < 0) {
                /*
                 * Добавляем запись в набор для вставки
                 */
                insertedRecords.add(editedDataRow);
            } else {
                /*
                 * Добавляем запись в набор для обновления
                 */
                updatedRecords.add(editedDataRow);
            }
        }

        // добавляем новые записи
        if (insertedRecords.size() > 0) {

            // создаем генератор SQL запросов
            SQLGenerator sql = new SQLGenerator(ti, restrictions);

            // получаем заготовку запроса
            Query query = sql.genInsertSQL();

            // создаем обертку для доступа к добавленным записям
            Rows insertedRows = new RowsImpl(insertedRecords.iterator(), restrictions);

            System.out.println("INSERT");
            try {
                qm.updateRows(query, insertedRows);
            } catch (SQLException e1) {
                System.out.println(query.getSQLQuery());
                e1.printStackTrace();
            }
        }

        // обновляем измененные
        if (updatedRecords.size() > 0) {

            // создаем генератор SQL запросов
            SQLGenerator sql = new SQLGenerator(ti, restrictions);

            // получаем заготовку запроса
            Query query = sql.genUpdateSQL();

            // создаем обертку для доступа к добавленным записям
            Rows updatedRows = new RowsImpl(updatedRecords.iterator(), restrictions);

            System.out.println("UPDATE");
            try {
                qm.updateRows(query, updatedRows);
            } catch (SQLException e1) {
                System.out.println(query.getSQLQuery());
                e1.printStackTrace();
            }
        }

        // удаляем удалённые
        if (deletedRecords.size() > 0) {

            // создаем генератор SQL запросов
            SQLGenerator sql = new SQLGenerator(ti, restrictions);

            // получаем заготовку запроса
            Query query = sql.genDeleteSQL();

            // создаем обертку для доступа к добавленным записям
            Rows deletedRows = new RowsImpl(deletedRecords.iterator(), restrictions);

            System.out.println("DELETE");
            try {
                qm.updateRows(query, deletedRows);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }
}
