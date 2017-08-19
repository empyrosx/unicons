/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import ru.diman.editors.uniedit.controllers.PageController;

/**
 * Действие "Удадить колонку"
 * @author Admin
 */
public class RemoveColumnAction extends AbstractAction{

    /**
     * Контроллер
     */
    private PageController controller;

    /**
     * Слушатель представления
     */
    private PageViewListener viewListener;

    /**
     * Конструктор
     * @param controller
     * @param viewListener
     */
    public RemoveColumnAction(PageController controller, PageViewListener viewListener) {

        //super("Удалить колонку");

        putValue(Action.SMALL_ICON, new ImageIcon("g:\\Diman\\resources\\removeColumn.png"));
        putValue(Action.SHORT_DESCRIPTION, "Удалить колонку");

        this.controller = controller;
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // получаем индекс текущей колонки
        int[] rows = viewListener.getSelectedRows();

        for (int i = 0; i < rows.length; i++){

            int rowIndex = rows[i];

            // удаляем колонку
            controller.removeColumn(rowIndex);
        }

    }
}
