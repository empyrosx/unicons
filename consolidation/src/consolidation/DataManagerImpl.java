/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

import consolidation.forms.model.DataSetModel;

/**
 *
 * @author ��������
 */
public class DataManagerImpl implements DataManager{

    /**
     * ������� ������
     */
    private int currentRow;

    /**
     * ������ ������
     */
    private DataSetModel model;

    /**
     * �����������
     * @param model ������ ������
     */
    public DataManagerImpl(DataSetModel model) {
        this.model = model;
    }

    @Override
    public Object getValue(String columnName) {
        return model.getValue(currentRow, columnName);
    }

    @Override
    public void setValue(String columnName, Object value) {
        model.setValueAt(value, currentRow, columnName);
    }

    /**
     * ��������� ������� ������
     * @return
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * ��������� ������� ������
     * @param currentRow
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    @Override
    public Object dereference(String code, Object value) {

        if (value == null){
            return "������ ��������";
        }
        else {
            String s = value.toString();
            return s.substring(1, 10);
            
        }
    }
}
