/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation.forms.model;

import javax.swing.table.AbstractTableModel;
import ru.diman.description.matrix.DataSet;

/**
 *
 * @author Admin
 */
public class DataSetModelX extends AbstractTableModel{

    /**
     * Данные модели
     */
    private DataSet data;

    /**
     * Конструктор
     * @param data данные
     */
    public DataSetModelX(DataSet data){
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return data.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.getValue(rowIndex, columnIndex);
    }

    /**
     * Тип модели данных
     */
    public enum ModelKind {

        /**
         * Фиксированная модель
         * Содержит фиксированный набор строк классификации. 
         */
        FIXED,

        /**
         * Линейная модель данных
         * Не содержит ни одной строки данных по умолчанию. Все строки вводятся пользователем.
         */
        LINE,

        /**
         * Частично фиксированная модель
         * Содержит фиксированную часть. Позволяет детализировать определенные части.
         */
        COMPLEX
    }
}
