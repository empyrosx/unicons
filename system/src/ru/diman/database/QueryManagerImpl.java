package ru.diman.database;

import ru.diman.system.database.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.matrix.DataSet;
import ru.diman.database.QueryManager;

public class QueryManagerImpl implements QueryManager {

    public void deleteReportData(int recordIndex) {

        // получаем менеджер БД
        DataBaseManager m = new DataBaseManager();

        // подключаемся к БД
        m.Connect();


        String s = "delete from utypedformsdetail where recordindex = " + String.valueOf(recordIndex);

        // готовим запрос
        PreparedStatement st = m.prepareStatement(s);

        System.out.println(s);

        try {
            int rc = st.executeUpdate();
            System.out.println(rc);
        } catch (SQLException ex) {
            Logger.getLogger(QueryManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        


    }

    /**
     * Загрузка данных
     * @param query запрос на выборку данных
     */
    public void loadRows(String query, DataSet rows) {

        // производим выборку из базы
        DataBaseManager m = new DataBaseManager();
        m.Connect();

        ResultSet rs = m.executeQuery(query);

        // перекачиваем данные в статиксет
        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            // получаем число колонок выборки
            int columnCount = rsmd.getColumnCount();

            int rowIndex = 0;

            // обходим все строки выборки
            while (rs.next()) {

                rows.addRow(rowIndex);

                // обходим все колонки текущей строки
                for (int i = 1; i <= columnCount; i++) {


                    //  получаем имя колонки
                    String aliasName = rsmd.getColumnName(i);

                    // получаем номер колонки
                    int columnIndex = Integer.parseInt(aliasName.substring(5));

                    // получаем данные колонки
                    Object columnData;

                    // преобразование по типам
                    int columnType = rsmd.getColumnType(i);
                    //System.out.println("Имя колонки = " + rsmd.getColumnName(i));
                    //System.out.println("Тип колонки = " + columnType);

                    if ((columnType == Types.DOUBLE) || (columnType == Types.NUMERIC)){
                        columnData = rs.getDouble(i);
                    }
                    else if ((columnType == Types.INTEGER) || (columnType == Types.SMALLINT)){
                        columnData = rs.getInt(i);
                    }
                    else {
                        columnData = rs.getString(i);
                    }

                    rows.setValue(rowIndex, columnIndex, columnData);
                }

                // переходим на следующую строку
                rowIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выполнение запроса для набора записей
     * @param query SQL - запрос
     * @param changedRows набор измененных записей
     * @throws SQLException
     */
    public void updateRows(Query query, Rows changedRows) throws SQLException {
        // получаем менеджер БД
        DataBaseManager m = new DataBaseManager();

        // подключаемся к БД
        m.Connect();

        System.out.println(query.getSQLQuery());

        // готовим запрос
        PreparedStatement st = m.prepareStatement(query.getSQLQuery());
        if (st != null) {

            // обходим все записи
            while (changedRows.hasNext()) {

                // получаем очередную запись
                Row row = changedRows.next();

                // получаем параметры запроса
                Params params = query.getParams();

                // если параметры указаны
                if (params != null) {

                    // получаем число параметров
                    int paramCount = params.getCount();

                    // устанавливаем параметры запроса
                    for (int i = 0; i < paramCount; i++) {

                        // получаем имя параметра
                        String paramName = params.getName(i);

                        // получаем тип параметра
                        ValueType valueType = params.getType(i);

                        // получаем значение параметра
                        Object paramValue = row.getValue(paramName);

                        //System.out.println(paramName + " = " + paramValue);

                        // получаем тип параметра в SQL
                        int sqlType = Types.INTEGER;

                        switch (valueType) {
                            case FLOAT:
                                sqlType = Types.DOUBLE;
                                break;
                            case STRING:
                                sqlType = Types.VARCHAR;
                                break;
                        }

                        // устанавливаем значение параметра в зависимости от типа
                        if (paramValue != null) {

                            if (sqlType == Types.INTEGER) {
                                st.setInt(i + 1, (Integer) paramValue);
                                //st.setString(i + 1, paramValue.toString());
                            } else if (sqlType == Types.DOUBLE) {
                                st.setDouble(i + 1, (Double) paramValue);
                                //st.setString(i + 1, paramValue.toString());
                            } else {
                                st.setString(i + 1, (String) paramValue);
                                //st.setString(i + 1, paramValue.toString());
                            }
                        } else {
                            st.setNull(i + 1, sqlType);
                        }
                    }
                }

                // выполняем запрос
                st.executeUpdate();
            }
        }
    }
}
