package consolidation.forms.model;

import consolidation.ScriptExecutor;
import javax.swing.table.AbstractTableModel;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.matrix.DataSet;


public class DataSetModel extends AbstractTableModel{

	private static final long serialVersionUID = 9204718075459408726L;
	
	// матрица
	protected DataSet matrix;
	
	// данные
	//protected DataSet data;
	
	// раскладка
	protected TableInfo ti;

	// только чтение
	boolean readOnly;
	

	/**
	 *  онструктор
	 * @param ti
	 * @param rows
	 */
	public DataSetModel(TableInfo ti, DataSet matrix, DataSet data) {
		// матрица
		this.matrix = matrix;
		this.ti = ti;
		
		// данные
		//this.data = data;
		
		/*
		
		for (int i = 0; i < data.getRowCount(); i++) {
			
			// получаем запись, закачанную из Ѕƒ
			DataRow dataRow = data.getRow(i);
			
			// ищем запись
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
	 * „исло колонок
	 */
	public int getColumnCount() {
		return ti.getCount();
	}

	/**
	 * „исло строк
	 */
	public int getRowCount() {
		return matrix.getRowCount();
	}

	/**
	 * ѕолучение значени€ €чейки
	 * @param rowIndex номер строки
	 * @param columnIndex номер колонки
	 */
    public Object getValueAt(int rowIndex, int columnIndex) {

        // если строка и колонка указаны, то возвращаем значение
        if ((rowIndex > -1) && (columnIndex > -1)) {
            // получаем колонку !!!
            //Column ci = ti.getItem(columnIndex);

            // получаем значение €чейки
            //return matrix.getValue(rowIndex, ci.getName());
            return matrix.getValue(rowIndex, columnIndex);
        } else {
            return null;
        }
    }

	/**
	 * ”становка значени€ €чейки
	 * @param aValue значение
	 * @param rowIndex номер строки
	 * @param columnIndex номер колонки
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
	 * @param rowIndex номер строки
	 * @param columnIndex номер колонки
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
	 * получение колонки по индексу
	 * @param index номер колонки
	 */
	public Column getColumn(int index) {
		return ti.getItem(index);
	}
	
	/**
	 * получение значени€ €чейки по номеру строку и имени колонки
	 */
	public Object getValue(int rowIndex, String columnName) {

		if (rowIndex > -1){
			return matrix.getValue(rowIndex, columnName);
		}
		return null;
	}

	/**
	 * ”становка модели только дл€ чтени€
	 * @param value
	 */
	public void setReadOnly(boolean value) {
		readOnly = value; 
	}
	
}
