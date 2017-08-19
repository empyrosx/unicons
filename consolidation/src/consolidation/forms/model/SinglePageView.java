/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consolidation.forms.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.swing.table.JTableX;
import ru.diman.swing.table.columns.TableColumnModelFactory;
import ru.diman.swing.table.columns.TableColumnModelX;

/**
 *
 * @author Admin
 */
public class SinglePageView extends JPanel {

    /**
     * Модель данных
     */
    private TableModel dataModel;
    /**
     * Раскладка
     */
    private TableInfo ti;
    /**
     * Сетка данных
     */
    private JTableX table;

    /**
     * Конструктор
     * @param dataModel модель данных
     * @param ti
     * @param imagesManager
     */
    public SinglePageView(TableModel dataModel, TableInfo ti) {

        this.dataModel = dataModel;
        this.ti = ti;

        setLayout(new BorderLayout(0, 0));

        // создаем модель колонок данной таблицы
        TableColumnModelX allColumnsModel = TableColumnModelFactory.createColumnModel(ti);

        // создаем сетку данных
        this.table = new JTableX(dataModel, allColumnsModel);

        this.table.setBorder(new TitledBorder(""));

        this.add(BorderLayout.CENTER, table.getContainer());
        table.setShowImagesPane(true);

//        Icon icon = new ImageIcon("g:\\Templates\\yellow.GIF");
//        table.getImagesManager().addImage(10, icon);
//        table.getImagesManager().addImage(20, icon);
        addColumnSort();


        for (int i = 0; i < table.getRowCount(); i++) {

            TableCellRenderer renderer = table.getCellRenderer(i, 0);
            Component component = table.prepareRenderer(renderer, i, 0);
            int height = component.getPreferredSize().height;
            // примечание
            //TableColumn tc = table.getColumnModel().getColumn(0);
            //TableCellRenderer cellRenderer = tc.getCellRenderer();
            //table.prepareRenderer(cellRenderer, i, 5);
            //int modelrow = table.convertRowIndexToModel(i);
            //int modelindex = 5;
            //int height = cellRenderer.getTableCellRendererComponent(table, dataModel.getValueAt(modelrow, modelindex), true, true, i, 0).getHeight();
            //if (height > 0) {
            table.setRowHeight(i, height);//}


        }

    }

    public JTableX getTable() {
        return table;
    }

    /**
     * Установка сортировки
     */
    private void addColumnSort() {

        RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(getTable().getModel());

        List<SortKey> keys = new ArrayList<SortKey>();

        for (int i = 0; i < ti.getCount(); i++) {
            Column ci = ti.getItem(i);
            //if (ci.getSortOrder() != SortOrder.UNSORTED){
            if ((ci.getColumnType() == ColumnType.KEY) || (ci.getColumnType() == ColumnType.INCREMENT_KEY)) {
                SortKey sortKey = new SortKey(i, SortOrder.ASCENDING);
                keys.add(sortKey);
            }
        }

        if (keys.size() > 0) {
            rowSorter.setSortKeys(keys);
            getTable().setRowSorter(rowSorter);
        }
    }
}
