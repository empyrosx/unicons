/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.system;

import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataSet;
import ru.diman.database.QueryRestrictions;

/**
 *
 * @author Admin
 */
public interface DataView {

    /**
     * Получение данных
     * Данные можно редактировать. Для сохранения изменений в БД нужно
     * вызвать метод ApplyUpdates
     * @return
     */
    public DataSet getData();

    /**
     * Сохранение изменений, сделанных над данными
     */
    public void applyUpdates();

    /**
     * Создание представления
     * @param ti раскладка
     * @param restrictions ограничения
     * @return
     */
    public DataView createView(TableInfo ti, QueryRestrictions restrictions);
}
