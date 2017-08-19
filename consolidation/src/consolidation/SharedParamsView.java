/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consolidation;

import java.awt.Component;
import java.text.ParseException;
import java.util.HashMap;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ValueType;
import ru.diman.swing.table.editors.FloatEditor;
import ru.diman.swing.table.editors.FormattedEditor;
import ru.diman.swing.table.editors.PickList;
import ru.diman.swing.table.editors.PickListEditor;
import ru.diman.swing.table.editors.TextFormatter;
import ru.diman.swing.table.editors.ZeroFormatter;
import ru.diman.swing.table.renderers.ColumnRendererFactory;
import ru.diman.swing.table.renderers.StandardRenderer;

/**
 *
 * @author Димитрий
 */
public class SharedParamsView extends JPanel {

    /**
     * Сетка данных
     */
    private JTable table;
    /**
     * Раскладка параметров
     */
    private TableInfo ti;

    /**
     * Конструктор
     * @param ti
     */
    public SharedParamsView(TableInfo ti) {

        this.ti = ti;
        this.table = new JTable();
        table.setRowSelectionAllowed(false);
        table.setRowHeight(24);

        TableModel dm = new DefaultTableModel(ti.getCount(), 2);
        for (int i = 0; i < ti.getCount(); i++) {
            dm.setValueAt(ti.getItem(i).getCaption(), i, 0);

            TableColumnModel cm = new DefaultTableColumnModel();

            TableColumn tc;

            tc = new TableColumn(0);
            tc.setHeaderValue("Имя параметра");
            cm.addColumn(tc);

            tc = new TableColumn(1);
            tc.setHeaderValue("Значение параметра");
            cm.addColumn(tc);
            tc.setCellRenderer(new MyRenderer());
            tc.setCellEditor(new MyEditor());

            table.setModel(dm);
            table.setColumnModel(cm);

            this.add(new JScrollPane(table));
            setSize(200, 600);
        }
    }

    public HashMap<String,Object> getValues(){
        HashMap<String,Object> result = new HashMap<String,Object>();

        for (int i = 0; i < ti.getCount(); i++){
            Column column = ti.getItem(i);
            result.put(column.getName(), table.getModel().getValueAt(i, 1));
        }


        return result;
    }


    private static TableCellEditor createColumnEditor(Column ci) {



        if (ci.getValueType() == ValueType.FLOAT) {
            return new FloatEditor();
        }

        // колонка с маской
        if (ci.getTextFormat() != "") {

            String textFormat = ci.getTextFormat();

            if (textFormat.indexOf('.') > 0) {
                // преобразуем маску в нужный формат
                String mask = textFormat;
                mask = mask.replace("\\", "");
                mask = mask.replace("0", "#");

                try {
                    TextFormatter formatter = new TextFormatter(mask);
                    return new FormattedEditor(formatter);
                } catch (ParseException e) {
                }
            } else {
                try {
                    ZeroFormatter formatter = new ZeroFormatter(textFormat);
                    return new FormattedEditor(formatter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        // выпадающий список
        if (!ci.getPickList().isEmpty()) {
            PickList pickList = new PickList(ci.getPickList(), ci.getValueType() == ValueType.INTEGER);
            return new PickListEditor(pickList);
        }

        return null;
    }

    public class MyEditor extends AbstractCellEditor implements TableCellEditor {

        private TableCellEditor editor;

        @Override
        public Object getCellEditorValue() {
            return editor.getCellEditorValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            // получаем колонку
            Column ci = ti.getItem(row);

            editor = createColumnEditor(ci);

            if (editor == null){
                editor = new DefaultCellEditor(new JTextField());
            }

            return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
    }

    public class MyRenderer implements TableCellRenderer{

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            Column ci = ti.getItem(row);


            // рисовальщик данных колонки
            TableCellRenderer r = ColumnRendererFactory.createRenderer(ci);
            if (r != null) {
                return new StandardRenderer(r).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            else {
                return new DefaultTableCellRenderer();
            }

        }

    }
}
