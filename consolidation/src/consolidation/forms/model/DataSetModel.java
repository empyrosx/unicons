package consolidation.forms.model;

import consolidation.ScriptExecutor;
import javax.swing.table.AbstractTableModel;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataSet;


public class DataSetModel extends AbstractTableModel{

	private static final long serialVersionUID = 9204718075459408726L;
	
	// �������
	protected DataSet matrix;
	
	// ������
	//protected DataSet data;
	
	// ���������
	protected TableInfo ti;

	// ������ ������
	boolean readOnly;
	

	/**
	 * �����������
	 * @param ti
	 * @param rows
	 */
	public DataSetModel(TableInfo ti, DataSet matrix, DataSet data) {
		// �������
		this.matrix = matrix;
		this.ti = ti;
		
		// ������
		//this.data = data;
		
		/*
		
		for (int i = 0; i < data.getRowCount(); i++) {
			
			// �������� ������, ���������� �� ��
			DataRow dataRow = data.getRow(i);
			
			// ���� ������
			int row = matrix.indexOf(dataRow);
			if (row > -1){

				for (int j = 0; j < ti.getCount(); j++) {
					ColumnInfo ci = ti.getItem(j);
					
					if (ci.getColumnType() != ColumnType.CALC){
						Object columnValue = data.getValue(i, j); 
						matrix.setValue(row, j, columnValue);
					}
				}
			}
			else{
				int rowIndex = matrix.getRowCount(); 
				matrix.addRow(rowIndex);
				for (int j = 0; j < ti.getCount(); j++) {
					ColumnInfo ci = ti.getItem(j);
					
					if (ci.getColumnType() != ColumnType.CALC){
						Object columnValue = data.getValue(i, j); 
						matrix.setValue(rowIndex, j, columnValue);
					}
				}
				
			}
		}
		*/
	}
	
	/**
	 * ����� �������
	 */
	public int getColumnCount() {
		return ti.getCount();
	}

	/**
	 * ����� �����
	 */
	public int getRowCount() {
		return matrix.getRowCount();
	}

	/**
	 * ��������� �������� ������
	 * @param rowIndex ����� ������
	 * @param columnIndex ����� �������
	 */
    public Object getValueAt(int rowIndex, int columnIndex) {

        // ���� ������ � ������� �������, �� ���������� ��������
        if ((rowIndex > -1) && (columnIndex > -1)) {
            // �������� ������� !!!
            //Column ci = ti.getItem(columnIndex);

            // �������� �������� ������
            //return matrix.getValue(rowIndex, ci.getName());
            return matrix.getValue(rowIndex, columnIndex);
        } else {
            return null;
        }
    }

	/**
	 * ��������� �������� ������
	 * @param aValue ��������
	 * @param rowIndex ����� ������
	 * @param columnIndex ����� �������
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		if ((rowIndex > -1) && (columnIndex > -1)) {
			// !!!
			Column ci = ti.getItem(columnIndex);
			matrix.setValue(rowIndex, ci.getName(), aValue);

                        String onChange = ci.getScriptOnChange();
                        if (!onChange.isEmpty()){

                            ScriptExecutor.executeScript(this, rowIndex, onChange);
                        }

			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
	
	public void setValueAt(Object aValue, int rowIndex, String columnName) {

		if (rowIndex > -1) {
			
			int columnIndex = ti.IndexOf(columnName);
			matrix.setValue(rowIndex, columnName, aValue);
			
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
	
	/**
	 * @param rowIndex ����� ������
	 * @param columnIndex ����� �������
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex){
		
		if (readOnly){
			return false;
		}
		
		/// !!! 
		//Column ci = ti.getItem(columnIndex);

		//if (ci.getReadOnly() && (ci.getPickList().isEmpty())) return false;
		
		//if (ci.getColumnType() == ColumnType.CALC) return false;
		
		return true;
	}
	
	/**
	 * ��������� ������� �� �������
	 * @param index ����� �������
	 */
	public Column getColumn(int index) {
		return ti.getItem(index);
	}
	
	/**
	 * ��������� �������� ������ �� ������ ������ � ����� �������
	 */
	public Object getValue(int rowIndex, String columnName) {

		if (rowIndex > -1){
			return matrix.getValue(rowIndex, columnName);
		}
		return null;
	}

	/**
	 * ��������� ������ ������ ��� ������
	 * @param value
	 */
	public void setReadOnly(boolean value) {
		readOnly = value; 
	}
	
}
