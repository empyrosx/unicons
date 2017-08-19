package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import ru.diman.description.TableInfo;
import ru.diman.description.Template;
import ru.diman.description.Templates;
import ru.diman.description.memento.DataReader;
import ru.diman.description.memento.XMLTemplatesReader;
import ru.diman.description.templates.TemplatesImpl;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;

/**
 * Действие "Открыть"
 * @author Диман
 *
 */
public class OpenKitAction extends AbstractAction{

	/**
	 *
	 */
	private static final long serialVersionUID = -6900529745209362452L;

	/**
	 * Контроллер
	 */
	private final Controller controller;

	/**
	 * Конструктор
	 * @param controller
	 */
	public OpenKitAction(Controller controller) {

		super("Открыть");

		this.controller = controller;

//        putValue(Action.SMALL_ICON, new ImageIcon("open.jpg"));
//        putValue(Action.LARGE_ICON_KEY, new ImageIcon("open_large.jpg"));
        putValue(Action.LARGE_ICON_KEY, new ImageIcon("g:\\Diman\\resources\\open2.png"));
        putValue(Action.SMALL_ICON, new ImageIcon("g:\\Diman\\resources\\open_small.png"));

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

        // выбор файла
        String fileName = FileDialogX.selectFile(null, "", ".kit");

        // если файл не указан, то выход
        if (fileName == null) {
            return;
        }

        // загружаем данные из файла
        while (controller.getCount() > 0){
            controller.remove(0);
        }

        //controller.loadFromFile(fileName);

        DataReader<Templates> reader = new XMLTemplatesReader(fileName);
        Templates templates = new TemplatesImpl();
        reader.loadData(templates);
        
        for (int i = 0; i < templates.getCount(); i++){

            Template template = templates.getItem(i);

            TableInfo ti = template.getTableInfo();

            int pageIndex = controller.add();
            PageController pageController = controller.getItem(pageIndex);
            pageController.addColumns(ti);
            controller.setPageName(pageIndex, template.getPageName());
        }
	}
}
