/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import ru.diman.description.column.ColumnImpl;
import ru.diman.editors.uniedit.controllers.PageController;

/**
 * Действие - добавить колонку
 * @author Admin
 */
public class AddColumnAction extends AbstractAction{

    /**
     * контроллер
     */
    private PageController controller;

    /**
     * Конструктор
     * @param controller
     */
    public AddColumnAction(PageController controller) {

        //super("Добавить колонку");

        putValue(Action.SHORT_DESCRIPTION, "Добавить колонку");
        final ImageIcon imageIcon = new ImageIcon("g:\\Diman\\resources\\addColumn.png");
        putValue(Action.SMALL_ICON,imageIcon);

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // добавляем колонку
        controller.addColumn(new ColumnImpl());
    }
}
