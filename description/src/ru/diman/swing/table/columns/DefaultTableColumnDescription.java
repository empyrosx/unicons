/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.swing.table.columns;

import java.text.ParseException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import ru.diman.description.Column;
import ru.diman.description.column.types.AutoSize;
import ru.diman.description.column.types.ValueType;
import ru.diman.swing.table.editors.FloatEditor;
import ru.diman.swing.table.editors.FormattedEditor;
import ru.diman.swing.table.editors.MultiRowEditor;
import ru.diman.swing.table.editors.PickList;
import ru.diman.swing.table.editors.PickListEditor;
import ru.diman.swing.table.editors.TextFormatter;
import ru.diman.swing.table.editors.ZeroFormatter;
import ru.diman.swing.table.renderers.ColumnRendererAdapter;
import ru.diman.swing.table.renderers.DecimalRenderer;
import ru.diman.swing.table.renderers.MultiRowRenderer;
import ru.diman.swing.table.renderers.NumberRenderer;
import ru.diman.swing.table.renderers.PickListRenderer;
import ru.diman.swing.table.renderers.TextFormatRenderer;

/**
 * Реализация TableColumnDescription для описания колонки Column
 * @author Димитрий
 */
public class DefaultTableColumnDescription implements TableColumnDescription {

    /**
     * Описание колонки
     */
    Column column;

    /**
     * Конструктор
     * @param column описание 
     */
    public DefaultTableColumnDescription(Column column) {
        this.column = column;
    }

    @Override
    public String getIdentifier() {
        return column.getName();
    }

    @Override
    public String getHeaderValue() {
        return column.getCaption();
    }

    @Override
    public int getPreferredWidth() {
        return column.getWidth() + 20;
    }

    @Override
    public boolean getCollapsed() {
        return column.getCollapsed();
    }

    @Override
    public int getLevel() {
        return column.getLevel();
    }

    @Override
    public TableCellRenderer getRenderer() {
        return createColumnRenderer(column);
    }

    @Override
    public TableCellEditor getEditor() {
        return createColumnEditor(column);
    }

    /**
     * Создание соответствующего декоратора для колонки
     * @param column
     * @return
     */
    private TableCellRenderer createColumnRenderer(Column column) {

        // выпадающий список
        if (!column.getPickList().isEmpty()) {
            PickList pickList = new PickList(column.getPickList(), column.getValueType() == ValueType.INTEGER);
            return new ColumnRendererAdapter(new PickListRenderer(pickList));
        }

        // суммы
        if (column.getValueType() == ValueType.FLOAT) {
            return new ColumnRendererAdapter(new DecimalRenderer());
        }

        // отформатированное значение (строковые и целые поля)
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

        if (column.getAutoSize().equals(AutoSize.AUTO_HEIGHT)) {
            return new MultiRowRenderer(column);
        }

        return new DefaultTableCellRenderer();
    }

    /**
     * Создание соответствующего редактора для колонки
     * @param column
     * @return
     */
    private TableCellEditor createColumnEditor(Column ci) {

        if (ci.getValueType() == ValueType.FLOAT) {
            return new FloatEditor();
        }

        // колонка с маской
        if (!ci.getTextFormat().isEmpty()) {

            String textFormat = ci.getTextFormat();

            if ((textFormat.indexOf('.') > 0) || (ci.getValueType() == ValueType.STRING)) {
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
        //System.out.println(ci.getName() + " = " + ci.getPickList());
        if (!ci.getPickList().isEmpty()) {
            PickList pickList = new PickList(ci.getPickList(), ci.getValueType() == ValueType.INTEGER);
            return new PickListEditor(pickList);
        }

        return new MultiRowEditor();
    }
}
