package consolidation.forms.rules;

/**
 * Интерфейс для получения уведомлений об изменении набора картинок
 * @author Диман
 *
 */
public interface RowImageListener {

	/**
	 * Уведомление об изменение картинки
	 * @param row номер строки, у которой произошла смена картинки
	 */
	public void imageChanged(int row); 
}
