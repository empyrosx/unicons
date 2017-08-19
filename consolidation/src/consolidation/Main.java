package consolidation;

import consolidation.forms.model.DataSetModelX.ModelKind;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import consolidation.forms.model.TableInfoModel;
import java.util.HashMap;
import ru.diman.database.QueryRestriction;
import ru.diman.database.QueryRestrictions;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;
import ru.diman.intities.EntityManager;
import ru.diman.system.handbooks.HandBookEditor;
import ru.diman.swing.table.JTableX;
import ru.diman.swing.table.columns.TableColumnModelFactory;
import ru.diman.swing.table.columns.TableColumnModelX;
import ru.diman.swing.table.columns.TableColumnX;
import ru.diman.system.DataView;
import ru.diman.system.DataViewImpl;
import ru.diman.system.Registry;
import ru.diman.system.handbooks.HandBookFactory;
import ru.diman.system.handbooks.HandBookFactoryImpl;

public class Main extends JFrame {

    //
    private static final long serialVersionUID = -1516091623046167563L;
    private TableInfoModel model;
    private final SharedParamsView sharedParamsView;
    private DataSet sourceData;
    private TableInfo ti;
    private QueryRestrictions restrictions;

    public Main() {

        // создаем раскладку страницы
        ti = createTableInfo();

        // создаем модель данных
        model = createModel();

        HandBookFactory hf = new HandBookFactoryImpl();

        // создаем модель колонок данной таблицы
        TableColumnModelX columnsModel = TableColumnModelFactory.createColumnModel(ti);

        //columnsModel.getColumn(0).setCellEditor(new HandBookEditor(new HandBookSelectorImpl(this, hf.getHandBook("тк_ФормаВвода"))));

        columnsModel.getColumn(4).setCellEditor(new HandBookEditor(hf.getHandBook("тк_Район")));
        //columnsModel.getColumn(5).setCellEditor(new HandBookEditor(hf.getHandBook("тк_ЛицевойСчет")));

        // создаем сетку данных
        final JTableX table = new JTableX(model, columnsModel);

        getContentPane().add(BorderLayout.CENTER, new JScrollPane(table));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        getContentPane().add(BorderLayout.NORTH, panel);
        sharedParamsView = new SharedParamsView(ti);


        getContentPane().add(sharedParamsView, BorderLayout.WEST);

        JButton addButton = new JButton();
        addButton.setText("Добавить заголовок");
        addButton.setPreferredSize(new Dimension(180, 32));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //model.addRow();

                TableColumnModelX cm = table.getColumnsModel();

                if (cm != null) {
                    TableColumnX tc = cm.getColumn(2);
                    tc.getHeader().setVisible(!tc.getHeader().isVisible());
                    table.refresh();
                }
            }
        });
        panel.add(addButton);


        JButton saveButton = new JButton();
        saveButton.setText("Выполнить");
        saveButton.setPreferredSize(new Dimension(180, 32));
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                HashMap<String, Object> values = sharedParamsView.getValues();

                // формируем запрос выборки
                restrictions = new QueryRestrictions();
                //restrictions.add(new QueryRestriction("КонсолидацияЗаголовки.КодФормы", 36102702));

                for (int i = 0; i < ti.getCount(); i++){
                    Column column = ti.getItem(i);

                    Object value = values.get(column.getName());
                    if (value != null){
                    restrictions.add(new QueryRestriction("КонсолидацияЗаголовки." + column.getName(), value));}
                }

                // формируем представление данных
                DataView dataView = new DataViewImpl(ti, restrictions);
                sourceData = dataView.getData();

                // создаем модель данных
                TableInfoModel result = new TableInfoModel(ti, new DataRowSet(ti), ModelKind.LINE);
                result.loadData(sourceData);

                // создаем модель колонок данной таблицы
                TableColumnModelX columnsModel = TableColumnModelFactory.createColumnModel(ti);

                table.setModel(result);
                table.setColumnsModelX(columnsModel);
                result.fireTableDataChanged();

                model = result;
            }
        });

        panel.add(saveButton);

        JButton showDataButton = new JButton();
        showDataButton.setText("Редактирование данные");
        showDataButton.setPreferredSize(new Dimension(180, 32));
        showDataButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame();

                int row = table.getSelectedRow();

                String fileName = (String) model.getValue(row, "Шаблон");

                int recordIndex = (Integer) model.getValue(row, "КодДокумента");

                Page page = new Page(recordIndex, fileName);

                frame.add(page);
                frame.setSize(1200, 480);
                frame.setVisible(true);
            }
        });

        panel.add(showDataButton);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.selectAll();


        int viewRowIndex = table.getSelectedRow();
        int modelRowIndex = table.convertRowIndexToModel(viewRowIndex);
        TableInfoModel m = (TableInfoModel)table.getModel();
        m.setValueAt("Изменено скриптом", modelRowIndex, "Наименование");

    }

    private TableInfoModel createModel() {

        // формируем запрос выборки
        restrictions = new QueryRestrictions();
        //restrictions.add(new QueryRestriction("СправочникФормВвода.КодГруппы", 36100200));
        //restrictions.add(new QueryRestriction("КонсолидацияЗаголовки.Год", 2008));
        restrictions.add(new QueryRestriction("КонсолидацияЗаголовки.КодФормы", 36101414));

        // формируем представление данных
        DataView dataView = new DataViewImpl(ti, restrictions);
        sourceData = dataView.getData();

        // создаем модель данных
        TableInfoModel result = new TableInfoModel(ti, new DataRowSet(ti), ModelKind.LINE);
        result.loadData(sourceData);

        return result;
    }

    private TableInfo createTableInfo() {

        String columns = "КонсолидацияЗаголовки.КодДокумента,СправочникФормВвода.Шаблон,КонсолидацияЗаголовки.КодФормы," +
                "КонсолидацияЗаголовки.Месяц,КонсолидацияЗаголовки.Год,КонсолидацияЗаголовки.НомерДокумента," +
                "КонсолидацияЗаголовки.Регион,КонсолидацияЗаголовки.ЛицевойСчет,КонсолидацияЗаголовки.ВидДеятельности";

        // получаем менеджер CLM
        EntityManager entityManager = Registry.getEntityManager();

        // создаем раскладку
        TableInfo result = entityManager.createTableInfo(columns);

        Column ci;

        ci = result.FindColumn("КодДокумента");
        ci.setVisible(false);
        ci.setColumnType(ColumnType.KEY);

        result.FindColumn("Шаблон").setVisible(false);

        ci = result.FindColumn("КодФормы");
        ci.setWidth(80);
        //ci.setColumnType(ColumnType.CAPTION);
        ci.setTextFormat("00.00.00.00");
        ci.setStandardHandBook("тк_ФормаВвода");
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("Месяц");
        ci.setWidth(80);
        String months = "Январь=1,Февраль=2,Март=3,Апрель=4,Май=5,Июнь=6,Июль=7,Август=8,Сентябрь=9,Октябрь=10,Ноябрь=11,Декабрь=12";
        months = months.replace(',', '\n');
        ci.setPickList(months);
        //ci.setLevel(1);
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("Год");
        ci.setWidth(80);
        //ci.setLevel(1);
        ci.setTextFormat("0000");
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("НомерДокумента");
        ci.setWidth(100);
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("Регион");
        ci.setTextFormat("00.00.00");
        ci.setWidth(100);
        ci.setStandardHandBook("тк_Район");
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("ЛицевойСчет");
        ci.setTextFormat("000.00.000.0");
        ci.setWidth(100);
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        ci = result.FindColumn("ВидДеятельности");
        ci.setPickList("Все=0\nБюджетная=1\nнебюджетная=2");
        ci.setWidth(100);
        ci.setColumnType(ColumnType.VALUE);
        ci.setVisible(true);

        return result;
    }

    /**
     * Запуск приложения
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 480);
        frame.setVisible(true);
    }
}
