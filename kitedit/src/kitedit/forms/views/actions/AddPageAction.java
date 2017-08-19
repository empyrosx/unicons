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
 * Действие "Добавить страницу"
 * @author Диман
 *
 */
public class AddPageAction extends AbstractAction{

	/**
	 *
	 */
	private static final long serialVersionUID = -996466486445974133L;

	/**
	 * Конструктор
	 */
	private final Controller controller;

	/**
	 * Конструктор
	 * @param controller
	 */
	public AddPageAction(Controller controller) {

		super("Добавить страницу");

		this.controller = controller;

        putValue(Action.SMALL_ICON, new ImageIcon("addpage.jpg"));
        putValue(Action.LARGE_ICON_KEY, new ImageIcon("addpage_large.jpg"));
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

        String pageName = JOptionPane.showInputDialog("Укажите имя страницы");
        if (!pageName.isEmpty()){

            // добавляем страницу
            int pageIndex = controller.add();

            // устанавливаем имя страницы
            PageController pageController = controller.getItem(pageIndex);
            pageController.setPageName(pageName);
        }
	}
}
