/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

/**
 * Интерфейс хранителя данных
 * @param <E>
 * @author Admin
 */
public interface DataWriter<E> {

    /**
     * Сохранить информацию об объекте
     * @param object объект, из которого необходимо считать информацию
     */
    public void saveData(E object);
}
