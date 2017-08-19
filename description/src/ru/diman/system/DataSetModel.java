package ru.diman.system;

import javax.swing.table.AbstractTableModel;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
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
	 * Конструктор
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

			// получаем запись, закачанную из БД
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
	 * Число колонок
	 */
	public int getColumnCount() {
		return ti.getCount();
	}

	/**
	 * Число строк
	 */
	public int getRowCount() {
		return matrix.getRowCount();
	}

	/**
	 * Получение значения ячейки
	 * @param rowIndex номер строки
	 * @param columnIndex номер колонки
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {

		// если строка и колонка указаны, то возвращаем значение
		if ((rowIndex > -1) && (columnIndex > -1)){
			// получаем колонку !!!
			Column ci = ti.getItem(columnIndex);

			// получаем значение ячейки
			return matrix.getValue(rowIndex, ci.getName());
		}
		else{
			return null;
		}
	}

	/**
	 * Установка значения ячейки
	 * @param aValue значение
	 * @param rowIndex номер строки
	 * @param columnIndex номер колонки
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		if ((rowIndex > -1) && (columnIndex > -1)) {
			// !!!
			Column ci = ti.getItem(columnIndex);
			matrix.setValue(rowIndex, ci.getName(), aValue);

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
		Column ci = ti.getItem(columnIndex);

		if (ci.getReadOnly()) return false;

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
	 * получение значения ячейки по номеру строку и имени колонки
	 */
	public Object getValue(int rowIndex, String columnName) {

		if (rowIndex > -1){
			return matrix.getValue(rowIndex, columnName);
		}
		return null;
	}

	/**
	 * Установка модели только для чтения
	 * @param value
	 */
	public void setReadOnly(boolean value) {
		readOnly = value;
	}

}
