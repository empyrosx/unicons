package ru.diman.swing.table.renderers;

import ru.diman.swing.table.*;
import java.text.NumberFormat;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DecimalRenderer implements ColumnRenderer{

	private static final long serialVersionUID = 6308946801196324851L;

	// формат
	private NumberFormat numberFormat;

	/**
	 * Конструктор
	 */
	public DecimalRenderer() {
		// создаем формат
		numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
	}

	/**
	 * Декорирование значения
     * @return
     */
    @Override
	public Object renderValue(Object value) {
		if (value != null){
			Double doubleValue = Double.parseDouble(value.toString());
			String formattedValue = numberFormat.format(doubleValue);
			return formattedValue;
		}
		return null;
	}

    @Override
	public void prepareRenderer(JComponent renderer) {
		if (renderer instanceof JLabel){
			((JLabel)renderer).setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}
}
