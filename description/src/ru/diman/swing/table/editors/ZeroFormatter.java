package ru.diman.swing.table.editors;

import java.text.DecimalFormat;
import javax.swing.text.NumberFormatter;

/**
 * Класс, представляющий из себя форматтер чисел по маске (без точек)
 * @author Димитрий
 *
 */
public class ZeroFormatter extends NumberFormatter{

	private static final long serialVersionUID = -5067950427328135658L;

	/**
	 * Констуктор
	 * @param textFormat маска ввода
	 */
	public ZeroFormatter(String textFormat) {
		super(new DecimalFormat());

		((DecimalFormat)getFormat()).setMinimumIntegerDigits(0);
		((DecimalFormat)getFormat()).setMaximumIntegerDigits(textFormat.length());

		// запрешаем некорректный ввод
		setAllowsInvalid(false);

		// это чтобы при получении фокуса писал новое значение, а не дописывал
		setOverwriteMode(true);
	}
}
