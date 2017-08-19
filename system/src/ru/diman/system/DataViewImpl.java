/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.system;

import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;
import ru.diman.database.QueryManagerX;
import ru.diman.database.QueryRestrictions;

/**
 *
 * @author Admin
 */
public class DataViewImpl implements DataView{

    /**
     * Раскладка колонок представления
     */
    private TableInfo ti;

    /**
     * Параметры ограничения представления
     */
    private QueryRestrictions restrictions;

    /**
     * Редактируемые данные
     */
    private DataSet viewData;

    /**
     * Данные базы данных (данные до редактирования)
     */
    private DataSet baseData;

    /**
     * Защищенный конструктор
     * @param ti раскладка
     * @param restrictions ограничения
     */
    public DataViewImpl(TableInfo ti, QueryRestrictions restrictions){

        this.ti = ti;
        this.restrictions = restrictions;

        QueryManagerX query = Registry.getQueryManager();
        baseData = query.selectData(ti, restrictions);
        viewData = new DataRowSet(ti);
        viewData.pumpData(baseData);
    }

    public DataSet getData() {
        return viewData;
    }

    public void applyUpdates() {
        DataSaver ds = new DataSaver();
        ds.saveData(baseData, viewData, ti, restrictions);
    }

    /**
     * Создание нового представления данных
     * @param ti раскладка
     * @param restrictions ограничения
     * @return
     */
    public DataView createView(TableInfo ti, QueryRestrictions restrictions) {
        return new DataViewImpl(ti, restrictions);
    }
}
