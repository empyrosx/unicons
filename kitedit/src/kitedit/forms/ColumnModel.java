package kitedit.forms;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.swing.table.PropertiesModel;
import ru.diman.swing.table.columns.DefaultTableColumnDescription;
import ru.diman.swing.table.columns.TableColumnModelX;
import ru.diman.swing.table.columns.TableColumnX;

public class ColumnModel extends TableColumnModelX {

    public ColumnModel(PropertiesModel model, TableInfo ti) {
        super();
        // создаем колонки
        createColumns(model, ti);
    }

    /**
     * создание колонок в модели
     */
    private void createColumns(PropertiesModel model, TableInfo ti) {
        for (int i = 0; i < ti.getCount(); i++) {
            // получаем очередное описание колонки
            Column ci = ti.getItem(i);
            // получаем индекс колонки в модели данных
            int columnIndex = model.getColumnIndex(ci.getName());
            // создаем колонку по описанию
            TableColumnX tc = new TableColumnX(columnIndex, new DefaultTableColumnDescription(ci));
            // добавляем колонку в набор
            addColumn(tc);
        }
    }
}
