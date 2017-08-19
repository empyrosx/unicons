/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import kitedit.forms.ColumnModel;
import kitedit.forms.views.actions.AddColumnAction;
import kitedit.forms.views.actions.PageViewListener;
import kitedit.forms.views.actions.RemoveColumnAction;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.ColumnImpl;
import ru.diman.description.column.types.HandlerKind;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.swing.table.JTableX;
import ru.diman.swing.table.columns.TableColumnModelX;
import ru.diman.swing.table.editors.EnumColumnEditor;
import ru.diman.uniedit.models.TableInfoModel;
import ru.diman.uniedit.views.UniPageView;

/**
 * Представление одной страницы KIT - шаблона
 * Связь с моделью реализуется через контроллер, передаваемый в конструкторе
 * @author Диман
 */
public class KitPageView extends JPanel implements UniPageView, PageViewListener{

    /**
     * Контроллер
     */
    private PageController controller;

    /**
     * Сетка данных
     */
    private JTableX table;

    /**
     * Конструктор
     * @param controller контроллер
     */
    public KitPageView(PageController controller) {

        // устанавлием раскладчик
        super(new BorderLayout(0, 0));

        setSize(300,300);

        // устанавливаем контроллер
        this.controller = controller;

        controller.addView(this);

        table = createTable();
        table.setAutoscrolls(true);

        add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.WEST, createToolBar());
    }

    /**
     * Создание сетки данных
     * @return 
     */
    protected JTableX createTable() {

        // получаем модель данных
        TableInfoModel dataModel = controller.getModel();

        // создаем раскладку для отображения
        TableInfo ti = createShowTableInfo();

        // создаем таблицу
        TableColumnModelX model = new ColumnModel(dataModel, ti);

        JTableX result = new JTableX(dataModel, model);

        // оптимальная высота
        result.setRowHeight(20);

        result.setDefaultEditor(ValueType.class, new EnumColumnEditor(ValueType.class));
        result.setDefaultEditor(HandlerKind.class, new EnumColumnEditor(HandlerKind.class));

        result.getColumn("HandlerInfo").setCellEditor(new HandlerInfoEditor(result));

        return result;
    }


    /**
     * Создание панели инструментов
     * @return
     */
    private JToolBar createToolBar(){

        JToolBar result = new JToolBar(JToolBar.VERTICAL);
        result.setFloatable(false);

        JButton addColumnButton = new JButton(new AddColumnAction(controller));
        addColumnButton.setFocusable(false);
        result.add(addColumnButton);

        JButton removeColumnButton = new JButton(new RemoveColumnAction(controller, this));
        removeColumnButton.setFocusable(false);

        result.add(removeColumnButton);

        return result;
    }

    // раскладка для визуального редактирования данных CLM файла
    private TableInfo createShowTableInfo() {

        TableInfo result = new TableInfoImpl();

        Column ci;

        // видимость
        ci = new ColumnImpl();
        ci.setCaption("Видимость");
        ci.setName("Visible");
        ci.setWidth(30);
        ci.setColumnClass(Boolean.class);
        result.addColumn(ci);

        // только чтение
        ci = new ColumnImpl();
        ci.setCaption("Только чтение");
        ci.setName("ReadOnly");
        ci.setWidth(30);
        ci.setColumnClass(Boolean.class);
        result.addColumn(ci);

        // имя колонки
        ci = new ColumnImpl();
        ci.setCaption("Имя колонки");
        ci.setName("Name");
        ci.setValueType(ValueType.STRING);
        ci.setWidth(150);
        result.addColumn(ci);

        ci = new ColumnImpl();
        ci.setCaption("Заголовок");
        ci.setName("Caption");
        ci.setValueType(ValueType.STRING);
        ci.setWidth(150);
        result.addColumn(ci);

        ci = new ColumnImpl();
        ci.setCaption("Ширина");
        ci.setName("Width");
        ci.setWidth(30);
        result.addColumn(ci);

        ci = new ColumnImpl();
        ci.setCaption("Обработчик");
        ci.setName("HandlerKind");
        ci.setWidth(100);
        ci.setColumnClass(HandlerKind.class);
        result.addColumn(ci);

        ci = new ColumnImpl();
        ci.setCaption("Данные обработчика");
        ci.setName("HandlerInfo");
        ci.setWidth(100);
        //ci.setColumnClass(HandlerKind.class);
        result.addColumn(ci);
        
        ci = new ColumnImpl();
        ci.setCaption("Формат");
        ci.setName("TextFormat");
        ci.setValueType(ValueType.STRING);
        ci.setWidth(100);
        result.addColumn(ci);

        // тип значения
        ci = new ColumnImpl();
        ci.setCaption("Тип значения");
        ci.setName("ValueType");
        ci.setValueType(ValueType.INTEGER);
        ci.setColumnClass(ValueType.class);
        ci.setWidth(80);
        ci.setReadOnly(true);
        result.addColumn(ci);

        return result;
    }

    public void refresh() {
        //setTitle(controller.getPageName());
    }

    public int[] getSelectedRows() {

        int row = -1;

        if ((row == -1) && (table.getRowCount() > 0)){
            row = table.getSelectionModel().getAnchorSelectionIndex();


            if (row == -1) {
                row = table.getSelectionModel().getLeadSelectionIndex();
            }
        }

        if ((row == -1) && (table.getRowCount() > 0)){
            row = 0;
        }

        if (row == -1){
            return new int[0];
        }
        else {
            return new int[]{row};
        }
    }
}
