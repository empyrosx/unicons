package ru.diman.swing.table.renderers;

import ru.diman.swing.table.renderers.ColumnRenderer;
import java.text.ParseException;
import javax.swing.JComponent;
import javax.swing.text.MaskFormatter;

/**
 * Рендерер для отображения значений по маске
 * @author Димитрий
 *
 */
public class TextFormatRenderer implements ColumnRenderer{

	private MaskFormatter formatter;

	public TextFormatRenderer(String textFormat){

		String s = textFormat;
		s = s.replace("\\", "");
		s = s.replace("0", "#");

		try {
			formatter = new Formatter(s);
			formatter.setValueContainsLiteralCharacters(false);
			formatter.setPlaceholderCharacter('0');
			formatter.setAllowsInvalid(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Formatter extends MaskFormatter{


		private static final long serialVersionUID = 4206362300002034524L;

		public Formatter(String mask) throws ParseException{
			super(mask);
		}

		public Object stringToValue(String value) throws ParseException {
			try{
				if (value == ""){
					return null;
				}
				else{
					return super.stringToValue(value);
				}
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			return value;
		}

		public String valueToString(Object value) throws ParseException {
			try{
				if (value == null){
					return "";
				}
				else {
					String sText = value.toString();

					String mask = getMask();
					mask = mask.replace(".", "");
					while (sText.length() < mask.length()){
						sText = "0" + sText;
					}
					return super.valueToString(sText);
				}
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			return (String) value;
		}

	}

	public Object renderValue(Object value){
		String formattedValue = null;

		if (value != null){
			formattedValue = value.toString();
			try {
				formattedValue = formatter.valueToString(value);
			}
			catch (ParseException e) {
			}
		}
		return formattedValue;
	}

	public void prepareRenderer(JComponent renderer) {
	};
}
