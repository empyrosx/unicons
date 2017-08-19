package consolidation.forms.rules.base;

/**
 * Интерфейс для доступа к данным в линейных контрольных соотношениях 
 * @author Диман
 *
 */
public interface LineValues extends Values{

	/**
	 * Получить значение строки
	 * @param row номер строки
	 */
	public Object getValue(int row);

	/**
	 * Установка значения строки
	 * @param row номер строки
	 * @param value значение
	 */
	public void setValue(int row, Object value); 

}
