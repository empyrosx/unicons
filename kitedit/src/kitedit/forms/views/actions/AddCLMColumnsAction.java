package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import kitedit.forms.ColumnSelectionDialog;
import ru.diman.uniedit.controllers.UniPageController;
import ru.diman.uniedit.views.listeners.ViewListener;
import ru.diman.description.TableInfo;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;


/**
 * Действие "Добавить колонки из CLM"
 * @author Диман
 *
 */
public class AddCLMColumnsAction extends AbstractAction{

	/**
	 *
	 */
	private static final long serialVersionUID = -5281022648734221841L;

	/**
	 * Контроллер
	 */
	private final Controller controller;

	/**
	 * Слушатель представления
	 */
	private final ViewListener viewListener;

	/**
	 * Конструктор
     * @param controller контроллер модели
     * @param viewListener слушатель представления
	 */
	public AddCLMColumnsAction(Controller controller, ViewListener viewListener) {

		super("Добавить колонки из CLM");

		this.controller = controller;
		this.viewListener = viewListener;

        putValue(Action.LARGE_ICON_KEY, new ImageIcon("addcolumnsfromclm_large.jpg"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

        // получаем индекс текущей страницы
        int pageIndex = viewListener.getCurrentPage();

        ColumnSelectionDialog dialog = null;

        if (dialog == null) {
            dialog = new ColumnSelectionDialog();
        }
        TableInfo ti = dialog.selectColumns();

        if (ti != null) {

            PageController pageController = controller.getItem(pageIndex);
            pageController.addColumns(ti);
        }
	}

}
