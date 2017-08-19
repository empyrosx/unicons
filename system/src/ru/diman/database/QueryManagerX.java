/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataSet;

/**
 * Интерфейс для получения данных из БД
 * @author Admin
 */
public interface QueryManagerX {

    /**
     * Выборка данных из БД
     * @param ti раскладка колонок для запроса
     * @param restrictions ограничения выборки
     * @return
     */
    public DataSet selectData(TableInfo ti, QueryRestrictions restrictions);

}
