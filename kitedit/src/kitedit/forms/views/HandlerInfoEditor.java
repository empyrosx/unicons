/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import kitedit.forms.views.EllipseEditor.EllipseHandler;
import ru.diman.description.column.types.HandlerKind;
import ru.diman.uniedit.models.TableInfoModel;

/**
 *
 * @author Admin
 */
public class HandlerInfoEditor extends AbstractCellEditor implements TableCellEditor{
    private JTable table;
    private Object value;

    /**
     * Конструктор
     * @param table
     */
    public HandlerInfoEditor(JTable table) {
        super();
        this.table = table;
    }

    
    public Object getCellEditorValue() {
        return value;
    }

    public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected, int row, int column) {

        // получаем вид обработчика
        TableInfoModel model = (TableInfoModel)table.getModel();
        int columnIndex = model.getColumnIndex("HandlerKind");
        HandlerKind handlerKind = (HandlerKind) model.getValueAt(row, columnIndex);
        this.value = value;

        // если это справочник, то показываем список доступных справочников
        if (handlerKind.equals(HandlerKind.HAND_BOOK)){
            return new JComboBox(new String[]{"тк_ФормаВвода", "тк_Район", "тк_ЛицСчет"});
        }
        else if (handlerKind.equals(HandlerKind.PICK_LIST)){

            EllipseEditor editor = new EllipseEditor(new EllipseHandler() {

                public Object selectValue(Object oldValue) {
                    //return new
                    PickListCreator pl = new PickListCreator(value == null ? "" : (String) value);
                    pl.setVisible(true);
                    HandlerInfoEditor.this.value = pl.getPickList();
                    return pl.getPickList();
                }
            });
            return editor.getTableCellEditorComponent(table, value, isSelected, row, column);


        }

        return new JTextField(value == null ? "" : (String)value);
    }

}
