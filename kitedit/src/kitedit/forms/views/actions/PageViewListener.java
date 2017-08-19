/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views.actions;

/**
 * Слушатель страницы
 * @author Admin
 */
public interface PageViewListener {

    /**
     * Получение индекса текущей колонки
     * @return
     */
    public int[] getSelectedRows();
}
