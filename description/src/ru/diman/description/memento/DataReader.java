/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

/**
 * Интерфейс загрузчика данных в объект E
 * @param <E>
 * @author Admin
 */
public interface DataReader<E> {

    /**
     * Загрузить информацию в объект
     * @param object объект, в который необходимо загрузить информацию
     */
    public void loadData(E object);
}
