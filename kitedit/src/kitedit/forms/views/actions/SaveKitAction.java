package kitedit.forms.views.actions;

import java.awt.event.ActionEvent;

import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import ru.diman.uniedit.controllers.UniPageController;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.Template;
import ru.diman.description.Templates;
import ru.diman.description.memento.DataWriter;
import ru.diman.description.memento.XMLTemplatesWriter;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.description.template.TemplateImpl;
import ru.diman.description.templates.TemplatesImpl;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.swing.table.PropertiesModel;
import ru.diman.uniedit.models.TableInfoModel;

/**
 * Действие "Сохранить"
 * @author Диман
 *
 */
public class SaveKitAction extends AbstractAction{

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
	public SaveKitAction(Controller controller) {

		super("Сохранить");

		this.controller = controller;

        putValue(Action.SMALL_ICON, new ImageIcon("save.jpg"));
        putValue(Action.LARGE_ICON_KEY, new ImageIcon("save_large.jpg"));
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		// запрашиваем имя файла для сохранения
        String fileName = FileDialogX.selectFile(null, "", ".KIT");


        // если файл не был выбран, то выходим
        if (fileName == null) {
            return;
        }

        Templates templates = new TemplatesImpl();
        for (int i = 0; i < controller.getCount(); i++) {

            PageController pageController = controller.getItem(i);

            Template template = new TemplateImpl();
            templates.add(template);

            // устанавливаем раскладку
            //PropertiesModel<Column> mmm = (PropertiesModel<Column>(pageController.getModel()));
            //Iterammm.getRows()

            TableInfo ti = new TableInfoImpl();

            TableInfoModel model = pageController.getModel();
            
            Iterator<Column> rows = model.getRows();
            while (rows.hasNext()){
                Column next = rows.next();
                ti.addColumn(next);
            }

            template.setTableInfo(ti);

            // устанавливаем имя страницы
            template.setPageName(pageController.getPageName());
        }

        DataWriter<Templates> writer = new XMLTemplatesWriter(fileName);
        writer.saveData(templates);
	}
}
