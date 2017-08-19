package ru.diman.swing.table.renderers;

import ru.diman.swing.table.*;
import ru.diman.swing.table.renderers.NumberRenderer;
import ru.diman.swing.table.renderers.MultiRowRenderer;
import ru.diman.swing.table.renderers.DecimalRenderer;
import ru.diman.swing.table.renderers.PickListRenderer;
import ru.diman.swing.table.editors.PickList;
import ru.diman.description.Column;
import ru.diman.description.column.types.ValueType;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import ru.diman.description.column.types.AutoSize;

/**
 * Фабрика создания декораторов для колонки
 * @author Димитрий
 *
 */
public class ColumnRendererFactory {

    /**
     * Создание соответствующего декоратора для колонки
     * @param column
     * @return
     */
    public static TableCellRenderer createRenderer(Column column) {

        // выпадающий список
        if (!column.getPickList().isEmpty()) {
            PickList pickList = new PickList(column.getPickList(), column.getValueType() == ValueType.INTEGER);
            return new ColumnRendererAdapter(new PickListRenderer(pickList));
        }

        // суммы
        if (column.getValueType() == ValueType.FLOAT) {
            return new ColumnRendererAdapter(new DecimalRenderer());
        }

        // отформатированное значение (строковые и целые поля
        if (!column.getTextFormat().isEmpty()) {

            // получаем формат
            String textFormat = column.getTextFormat();

            if ((textFormat.indexOf('.') > 0) || (column.getValueType() == ValueType.STRING)) {
                // с точками
                return new ColumnRendererAdapter(new TextFormatRenderer(column.getTextFormat()));
            } else {
                // целые без '.'
                return new ColumnRendererAdapter(new NumberRenderer(textFormat));
            }
        }

        /*
        if (column.getAliasName().equalsIgnoreCase("note")){
        return new MultiRowRenderer(column);
        }*/

        if (column.getAutoSize().equals(AutoSize.AUTO_HEIGHT)){
            return new MultiRowRenderer(column);
        }


        return new DefaultTableCellRenderer();
    }
}
