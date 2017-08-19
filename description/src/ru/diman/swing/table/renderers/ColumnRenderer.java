package ru.diman.swing.table.renderers;

import javax.swing.JComponent;


/**
 * Общий интерфейс для украшения значений
 * @author Димитрий
 *
 */
public interface ColumnRenderer {

	/**
	 * Получить приукрашенное значение
	 * @param value реальное значение
	 */
	public Object renderValue(Object value);

	/**
	 * Специфическая обработка компонента отображения
	 */
	public void prepareRenderer(JComponent renderer);
}
