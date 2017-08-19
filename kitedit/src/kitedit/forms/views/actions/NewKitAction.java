package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.uniedit.controllers.UniController;

/**
 * Действие "Создать новый шаблон"
 * @author Диман
 *
 */

public class NewKitAction extends AbstractAction{

	/**
	 *
	 */
	private static final long serialVersionUID = -5640011718875693905L;

	/**
	 * Контроллер
	 */
	private final Controller controller;

	/**
	 * Конструктор
     *
     * @param controller контроллер
     */
	public NewKitAction(Controller controller){
		super("Создать новый");

		this.controller = controller;

        //putValue(Action.SMALL_ICON, new ImageIcon("new.jpg"));
        //putValue(Action.LARGE_ICON_KEY, new ImageIcon("new_large.jpg"));
        putValue(Action.LARGE_ICON_KEY, new ImageIcon("g:\\Diman\\resources\\newPage2.png"));
        putValue(Action.SMALL_ICON, new ImageIcon("g:\\Diman\\resources\\newPage_small.png"));

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		// удаляем все страницы
        while (controller.getCount() > 0){
            controller.remove(0);
        }

        // запрашиваем имя новой страницы
        String pageName = JOptionPane.showInputDialog("Укажите имя страницы");

        // если имя указано, то ...
        if (!pageName.isEmpty()){

            // ... добавляем страницу
            int pageIndex = controller.add();

            // ... устанавливаем имя страницы
            controller.setPageName(pageIndex, pageName);
        }
	}
}
