/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.database.QueryManager.Params;

/**
 * Интерфейс для описания SQL - запроса
 * @author Admin
 */
public interface Query {

    /**
     * Строковое представление запроса
     * @return
     */
    public String getText();

    /**
     * Получение параметров запроса
     * @return
     */
    public Params getParams();
}
