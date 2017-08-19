package ru.diman.swing.table.editors;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.TextAction;

public class FormattedEditor extends DefaultCellEditor{

	private static final long serialVersionUID = 1606207698398786175L;

	// поле ввода
	private JFormattedTextField ftf;

	// форматтер
	private DefaultFormatter formatter;

	/**
	 * Конструктор
	 * @param formatter форматтер
	 * @throws ParseException
	 */
	public FormattedEditor(DefaultFormatter formatter) throws ParseException{
		super(new JFormattedTextField(formatter){

			private static final long serialVersionUID = 7485682603795415931L;

			/**
			 * Перекрываем для того, чтобы по Escape шла просто отмена редактирования, а не удаление значения
			 */
		    public Action[] getActions() {
		    	Action[] actions = super.getActions();

		    	int index = 0;
		    	Action[] newActions = new Action[actions.length - 1];
		    	for (int i = 0; i < actions.length; i++) {
					Action action = actions[i];
					String name = (String) action.getValue("Name");
					if (!name.equalsIgnoreCase("reset-field-edit")){
						newActions[index] = action;
						index++;
					}
				}

		        return TextAction.augmentList(newActions, new Action[]{});
		    }

		});

		ftf = (JFormattedTextField)getComponent();
		ftf.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

		this.formatter = formatter;
	};


    // перекрываем метод для того, чтобы установить начальное значение для ввода
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

    	//super.getTableCellEditorComponent(table, value, isSelected, row, column);
        /*
         * По идее нужно устанавливать значение через ftf.setValue(value),
         * однако в случае когда value != null клавиши Delete & BackSpace не работают.
         *
         *   Если проделывать установку значения через ftf.setText(), то всё работает нормально
         */

        // форматируем значение сами
        String stringValue = null;

        try {

        	if ((formatter instanceof MaskFormatter) && (value != null)){
    			String sText = value.toString();


    			String mask = ((MaskFormatter)formatter).getMask();
    			mask = mask.replace(".", "");
    			while (sText.length() < mask.length()){
    				sText = "0" + sText;
    			}
    			stringValue = formatter.valueToString(sText);
        	}
        	else{
        		stringValue = value == null ? "" : formatter.valueToString(value);
        	}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// устанавливаем форматированное значение
		ftf.setValue(null);
        ftf.setText(stringValue);

       return ftf;
    }


    // перекрываем метод для того, чтобы вернуть введенное значение
    // по умолчанию DefaultCellEditor возвращает ftf.GetText()
    public Object getCellEditorValue() {
        JFormattedTextField ftf = (JFormattedTextField)getComponent();

        // расформатируем значение сами
        String strValue = ftf.getText();

        Object value = null;

        try {
			value = formatter.stringToValue(strValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}

        return value;
    }
}
