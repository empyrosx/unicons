package ru.diman.swing.table.editors;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * Наследник MaskFormmater
 *		При вводе не всех символов из маски заменяет их на 0
 */
public class TextFormatter extends MaskFormatter{

	/**
	 *
	 */
	private static final long serialVersionUID = 4187880326253860335L;

	public TextFormatter(String mask) throws ParseException{

		mask = mask.replace("\\", "");
		mask = mask.replace("0", "#");
		setMask(mask);

		setAllowsInvalid(false);
		setOverwriteMode(true);
		setPlaceholderCharacter('_');
		setValueContainsLiteralCharacters(false);
	}

	/*
	 *	Преобразуем введенное значение в нужное нам
	 */

	public Object stringToValue(String string) throws ParseException{

		String mask = getMask();
		mask = mask.replace('#', getPlaceholderCharacter());

		// если значение не ввели, то возвращаем null
		if (string.equals(mask)){
			return null;
		}
		else{
			// заменяем все невведённые символы на 0
			String val = string.replace(getPlaceholderCharacter(), '0');
			Object value = super.stringToValue(val);
			return value;
		}
	}
}
