/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;

/**
 *
 * @author Admin
 */
public class QueryManagerXImpl implements QueryManagerX{

    private QueryManager qm;

    public QueryManagerXImpl() {

        this.qm  = new QueryManagerImpl();

    }

    public DataSet selectData(TableInfo ti, QueryRestrictions restrictions) {

        // загружаем данные из базы
        DataSet result = new DataRowSet(ti);

        // формируем запрос выборки
        SQLGenerator gen = new SQLGenerator(ti, restrictions);
        String query = gen.generateSQL();

        // загружаем данные из базы
        qm.loadRows(query, result);

        return result;
    }

}
