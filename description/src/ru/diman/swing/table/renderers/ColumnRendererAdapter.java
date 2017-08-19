package ru.diman.swing.table.renderers;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Класс, представляющий собой адаптер для ColumnRenderer
 * @author Димитрий
 *
 */
public class ColumnRendererAdapter implements TableCellRenderer{

	// рендерер значения
	private ColumnRenderer renderer;

	// компонент для отображения
	JLabel component;
	/**
	 * Конструктор
	 * @param renderer
	 */
	public ColumnRendererAdapter(ColumnRenderer renderer){
		this.renderer = renderer;
		this.component = new JLabel();
	}

	/**
	 * Получение компонента для отображения
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (renderer != null){

			// получаем значение
			Object rendererValue = renderer.renderValue(value);

			// устанавливаем
			component.setText(rendererValue == null ? "" : rendererValue.toString());

			// дополнительная настройка
			renderer.prepareRenderer(component);
		};

		return component;
	}

}
