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
     * ������ ������
     */
    private DataSet data;

    /**
     * �����������
     * @param data ������
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
     * ��� ������ ������
     */
    public enum ModelKind {

        /**
         * ������������� ������
         * �������� ������������� ����� ����� �������������. 
         */
        FIXED,

        /**
         * �������� ������ ������
         * �� �������� �� ����� ������ ������ �� ���������. ��� ������ �������� �������������.
         */
        LINE,

        /**
         * �������� ������������� ������
         * �������� ������������� �����. ��������� �������������� ������������ �����.
         */
        COMPLEX
    }
}
