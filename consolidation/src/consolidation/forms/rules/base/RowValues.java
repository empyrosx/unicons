package consolidation.forms.rules.base;

/**
 * Интерфейс для доступа к данным в строковых контрольных соотношениях 
 * @author Диман
 *
 */

public interface RowValues extends Values{

	/**
	 * Получение значения колонки
	 * @param columnName имя колонки
	 */
	public Object getValue(String columnName);
	
	/**
	 * Установка значения колонки
	 * @param columnName имя колонки
	 * @param value значение
	 */
	public void setValue(String columnName, Object value);

}
