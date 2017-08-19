/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Admin
 */
public class EllipseEditor extends AbstractCellEditor implements TableCellEditor{

	// значение колонки
	private Object value;

	// редактор
	private JTextField editor;

	// кнопка вызова обработчика
	private JButton editButton;

    /**
     * Конструктор
     * @param handler 
     */
    public EllipseEditor(final EllipseHandler handler) {

		// создаем редактор для отображения кнопки
		editor = new JTextField();
		editor.setLayout(new BorderLayout(0, 0));
		editor.setEditable(false);


		// создаем кнопку для вызова модального справочника
		editButton = new JButton();
		editButton.setPreferredSize(new Dimension(16, 16));
		editButton.setText("...");
		editButton.addActionListener(new ActionListener(){

			/**
			 * Обработчик на нажатие
			 */
			public void actionPerformed(ActionEvent e) {

				// устанавливаем значение по умолчанию
                Object selectedValue = handler.selectValue(value);

                if (selectedValue != null){
                    value = selectedValue;
                }

				// заканчиваем редактирование
				fireEditingStopped();
			}
		});

		// добавляем нашу кнопку в редактор
		editor.add(BorderLayout.EAST, editButton);
    }

    public Object getCellEditorValue() {
        return value;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// получаем рисовальщик ячейки
		TableCellRenderer renderer = table.getCellRenderer(row, column);
		if (renderer != null){

			// получаем компонент для отображения
			table.prepareRenderer(renderer, row, column);
			Component comp = renderer.getTableCellRendererComponent(table, value, isSelected, false, row, column);

			// копируем значение
			// если рендерер не JLabel, то как получить форматированное значение?
			if (comp instanceof JLabel){
				editor.setText(((JLabel)comp).getText());
			}
			else{
				editor.setText(value == null ? "" : value.toString());
			}

			// копируем размеры в наш редактор
			Dimension size = comp.getPreferredSize();

			editor.setPreferredSize(size);
			editor.setBackground(comp.getBackground());
			editButton.setSize(editButton.getSize().width, size.height);
		}

		this.value = value;
		return editor;
    }

    /**
     * Интерфейс, отвечающий за выбор нового значения
     */
    public interface EllipseHandler{

        public Object selectValue(Object oldValue);
    }
}
