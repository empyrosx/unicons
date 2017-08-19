package ru.diman.system;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.matrix.DataRow;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;

public class TableInfoModel extends DataSetModel {

	private static final long serialVersionUID = -3558449768116208864L;
	/**
	 * конструктор
	 * @param ti
	 * @param data TODO
	 */
	public TableInfoModel(TableInfo ti, DataSet rows){
		super(ti, rows, null);
	}


	/**
	 * Загрузка жестко фиксированной формы
	 */
	public void fixedLoadData(DataSet data){

		// обходим все строки данных
		for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

			// получаем очередную запись
			DataRow dataRow = data.getRow(rowIndex);

			// ищем запись в матрице
			int matrixRow = matrix.indexOf(dataRow);

			if (matrixRow > -1){

				// копируем все поля, кроме вычислимые (они прописаны в шаблоне и не сохраняются вовне)
				for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

					// получаем очередную колонку
					Column ci = ti.getItem(columnIndex);

					if (ci.getColumnType() != ColumnType.CALC){

						// получаем значение колонки
						Object columnValue = data.getValue(rowIndex, columnIndex);

						// устанавливаем значение
						matrix.setValue(matrixRow, columnIndex, columnValue);
					}
				}
			}
			else{
				// выводим сообщение о том, что не для строки не нашлось места
				System.out.println("Запись не найдена в шаблоне. Атрибуты записи следующие:");

				for (int i = 0; i < ti.getCount(); i++) {

					// выводим атрибуты
					System.out.println(ti.getItem(i).getName() + "=" + dataRow.getValue(i));
				}
			}
		}
	}


	/**
	 * Загрузка жестко фиксированной формы
	 */
	public void lineLoadData(DataSet data){

		// обходим все строки данных
		for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

			matrix.addRow(rowIndex);

			// копируем все поля, кроме вычислимые (они прописаны в шаблоне и не сохраняются вовне)
			for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

				// получаем очередную колонку
				Column ci = ti.getItem(columnIndex);

				//if (ci.getColumnType() != ColumnType.CALC){

					// получаем значение колонки
					Object columnValue = data.getValue(rowIndex, columnIndex);

					// устанавливаем значение
					matrix.setValue(rowIndex, columnIndex, columnValue);
				//}
			}
		}

	}


	/**
	 * Загрузка жестко фиксированной формы
	 */
	public void mixedLoadData(DataSet data){

		// обходим все строки данных
		for (int i = 0; i < data.getRowCount(); i++) {

			// получаем запись, закачанную из БД
			DataRow dataRow = data.getRow(i);

			// ищем запись
			int row = matrix.indexOf(dataRow);
			if (row > -1){

				for (int j = 0; j < ti.getCount(); j++) {
					Column ci = ti.getItem(j);

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
					Column ci = ti.getItem(j);

					if (ci.getColumnType() != ColumnType.CALC){
						Object columnValue = data.getValue(i, j);
						matrix.setValue(rowIndex, j, columnValue);
					}
				}
			}
		}
	}


	public DataSet getData(){

		DataSet result = new DataRowSet(ti);

		int rowIndex = 0;
		for (int i = 0; i < matrix.getRowCount(); i++) {

			DataRow dataRow = matrix.getRow(i);
			if (1==1){/*(dataRow.hasValues()){*/
				// добавляем новую запись
				result.addRow(result.getRowCount());

				for (int j = 0; j < ti.getCount(); j++) {
					result.setValue(rowIndex, j, dataRow.getValue(j));
				}
				rowIndex++;
			}

		}

		return result;

	}


    public Class<?> getColumnClass(int columnIndex){
    	Column ci = ti.getItem(columnIndex);

    	if (ci.getValueType() == ValueType.INTEGER){
    		return Integer.class;
    	}
    	else{
    		return String.class;
    	}

    }


	/**
	 * раскладка
	 */
	public TableInfo getTableInfo(){
		return ti;
	}

	/**
	 * добавление строки в конец
	 */
	public void addRow(){
		matrix.addRow(matrix.getRowCount());
		fireTableRowsInserted(matrix.getRowCount() - 1, matrix.getRowCount() - 1);
	}

}
