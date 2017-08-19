package consolidation;

import consolidation.forms.model.DataSetModelX.ModelKind;
import consolidation.forms.model.SinglePageModel;
import consolidation.forms.model.SinglePageView;
import consolidation.forms.model.TableInfoModel;
import consolidation.forms.rules.LineRulesManager;
import consolidation.forms.rules.RowRulesManager;
import consolidation.forms.rules.line.LineRule;
import consolidation.forms.rules.parsers.LineRuleParser;
import consolidation.forms.rules.parsers.RowRuleParser;
import consolidation.forms.rules.row.RowRule;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JPanel;
import ru.diman.database.QueryRestrictions;
import ru.diman.description.TableInfo;
import ru.diman.description.Template;
import ru.diman.description.matrix.DataSet;
import ru.diman.swing.table.ImagesManager;
import ru.diman.swing.table.JTableX;
import ru.diman.system.DataView;
import ru.diman.system.DataViewImpl;

public class SinglePage extends JPanel {

    /**
     * Раскладка колонок данной страницы
     */
     private TableInfo ti;

     /**
      * Исходные данные 
      */
     private DataSet sourceData;

     /**
      * Представление данных
      */
     DataView dataView;


    private static final long serialVersionUID = 6315028095807427443L;
    private TableInfoModel datamodel;
    private SinglePageView view;

    public SinglePage() {
        setLayout(new BorderLayout());
        setSize(1000, 1000);
    }

    /**
     * Сохранить отредактированные данные
     */
    public void saveData(){
        //DataSaver ds = new DataSaver();
        //ds.saveData(sourceData, datamodel.getData(), ti, restrictions);
        DataSet data = dataView.getData();
        data.clearData();
        data.pumpData(datamodel.getData());
        dataView.applyUpdates();
    }

    /**
     * Построение страницы по переданной страницы шаблона формы ввода
     * @param template
     * @param restrictions ограничения
     */
    public void loadTemplate(Template template, QueryRestrictions restrictions) {

        // получаем раскладку страницы
        this.ti = template.getTableInfo();

        // создаем представление
        dataView = new DataViewImpl(ti, restrictions);
        this.sourceData = dataView.getData();

        // получаем матрицу данных
        DataSet matrix = template.getMatrix();

        ModelKind modelKind = null;

        if (matrix.getRowCount() == 0){
            modelKind = ModelKind.LINE;
        }
        else {
            if (template.canAddRows()){
                modelKind = ModelKind.COMPLEX;
            }
            else {
                modelKind = ModelKind.FIXED;
            }
        }

        // создаем модель данных

        datamodel = new TableInfoModel(ti, matrix, modelKind);
        datamodel.loadData(sourceData);

        SinglePageModel model = new SinglePageModel(datamodel);

        view = new SinglePageView(datamodel, ti);
        ImagesManager imagesManager = view.getTable().getImagesManager();

        add(BorderLayout.CENTER, view);

        // получаем набор строковых правил
        String rules = template.getRowRules();

        if (rules != null) {

            // создаем парсер строковых правил
            RowRuleParser parser = new RowRuleParser();

            // разбираем набор правил
            RowRule[] rowRules = parser.parseRules(rules);

            // создаем менеджер для управления правилами
            RowRulesManager rowRulesManager = new RowRulesManager(rowRules, datamodel, imagesManager);

            // устанавливаем его у таблицы
            //table.setRowRulesManager(rowRulesManager);
            model.setRowRulesManager(rowRulesManager);

            rowRulesManager.check();
        }


        // получаем набор строковых правил
        String sLineRules = template.getLineRules();
        if (sLineRules != null) {
            // создаем парсер строковых правил
            LineRuleParser parser = new LineRuleParser(matrix);

            // разбираем набор правил
            LineRule[] lineRules = parser.parseRules(sLineRules);

            // создаем менеджер для управления правилами
            final LineRulesManager lineRulesManager = new LineRulesManager(lineRules, datamodel, imagesManager);

            // устанавливаем его у таблицы
            model.setLineRulesManager(lineRulesManager);

            lineRulesManager.check();

            view.getTable().setBaseRenderer(new JTableX.TableRenderer() {

                @Override
                public void renderComponent(JComponent component, int row, int column) {

                    if ((lineRulesManager != null) && (lineRulesManager.isRowCalculated(row))) {
                        Color c = new Color(180, 255, 180);
                        component.setBackground(c);
                    }
                }
            });


        }
         
        /*
        final JList rowHeader = new RowHeader(table, imagesManager);
        scrollPane.setRowHeaderView(rowHeader);
        //table.setList(rowHeader);

        this.add(BorderLayout.CENTER, scrollPane);

        JButton b = new JButton("Сохранить");
        b.addActionListener(new ActionListener(){

        public void actionPerformed(ActionEvent e) {

        DataSaver dataSaver = new DataSaver();
        dataSaver.saveData(data, datamodel.getData(), ti, restrictions);
        }

        });

        this.add(BorderLayout.NORTH, b);

        /*
        JButton c = new JButton("Обновить");
        c.addActionListener(new ActionListener(){

        public void actionPerformed(ActionEvent e) {
        rowHeader.updateUI();
        }

        });
        this.add(BorderLayout.NORTH, c);
         */
    }

    public TableInfoModel getModel() {
        return datamodel;
    }

    public JTableX getTable() {
        return view.getTable();
    }
}
