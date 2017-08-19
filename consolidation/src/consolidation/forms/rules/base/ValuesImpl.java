package consolidation.forms.rules.base;

import consolidation.forms.model.TableInfoModel;
import consolidation.forms.rules.base.Values;


/**
 * Реализация объекта для доступа к данным
 * @author Диман
 *
 */
public class ValuesImpl implements Values, RowValues, LineValues{

	// модель данных 
	TableInfoModel model;
	
	// номер текущей строки
	private int rowIndex;
	
	// имя текущей колонки
	private String columnName;

	/**
	 * Создание объекта доступа к данным для текущей строки
	 * @param model модель данных
	 * @param rowIndex номер текущей строки
	 */
	public ValuesImpl(TableInfoModel model, int rowIndex){
		this.model = model;
		this.rowIndex = rowIndex;
	}

	/**
	 * Создание объекта доступа к данным для текущей колонки
	 * @param model модель данных
	 * @param columnName имя текущей колонки
	 */
	public ValuesImpl(TableInfoModel model, String columnName){
		this.model = model;
		this.columnName = columnName;
	}

	/**
	 * Получение значения колонки
	 * @param columnName имя колонки
	 */
	public Object getValue(String columnName) {
		return model.getValue(rowIndex, columnName);
	}

	/**
	 * Получить значение строки
	 * @param row номер строки
	 */
	public Object getValue(int row) {
		return model.getValue(row, columnName);
	}

	/**
	 * Установка значения колонки
	 * @param columnName имя колонки
	 * @param value значение
	 */
	public void setValue(String columnName, Object value) {
		
		if ((value instanceof Double) && (value.equals(0.0))){
			model.setValueAt(null, rowIndex, columnName);
		}
		else{
			model.setValueAt(value, rowIndex, columnName);
		}
	}

	/**
	 * Установка значения строки
	 * @param row номер строки
	 * @param value значение
	 */	
	public void setValue(int row, Object value) {
		if ((value instanceof Double) && (value.equals(0.0))){
			model.setValueAt(null, row, columnName);
		}
		else{
			model.setValueAt(value, row, columnName);
		}
	}
}
