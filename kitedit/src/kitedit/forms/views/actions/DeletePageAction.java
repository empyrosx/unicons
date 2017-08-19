package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.uniedit.views.listeners.ViewListener;

/**
 * Действие "Удалить страницу"
 * @author Диман
 *
 */
public class DeletePageAction extends AbstractAction{

	/**
	 *
	 */
	private static final long serialVersionUID = -996466486445974133L;

	/**
	 * Конструктор
	 */
	private final Controller controller;

	/**
	 * Слущатель предствления
	 */
	private final ViewListener viewListener;

	/**
	 * Конструктор
     * @param controller контроллер модели
     * @param viewListener слушатель представления
	 */
	public DeletePageAction(Controller controller, ViewListener viewListener) {

		super("Удалить страницу");

		this.controller = controller;
		this.viewListener = viewListener;

        putValue(Action.LARGE_ICON_KEY, new ImageIcon("deletepage_large.jpg"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

        // получаем индекс текущей страницы
        int pageIndex = viewListener.getCurrentPage();

        // удаляем её
        controller.remove(pageIndex);
	}
}
