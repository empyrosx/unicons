/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JTable;

/**
 * Стандартное представление для заголовков строк сетки данных
 * @author Admin
 */
public class DefaultRowHeader extends JList{

	/**
     * Сетка данных
     */
	private JTableX table;

	/**
	 * Конструктор
     * @param table
     */
	public DefaultRowHeader(final JTableX table) {

		this.table = table;

		// устанавливаем модель таблицы
		setModel(new RowHeaderModel());

		setFixedCellWidth(36);
		setBackground(Color.GRAY);

		setCellRenderer(new RowHeaderRenderer(table));
		setFocusable(false);
	}

	/**
	 * Модель таблицы заголовков
	 * @author Димитрий
	 *
	 */
	class RowHeaderModel extends AbstractListModel{

		/**
		 * Элемент по индексу
		 */
        @Override
		public Object getElementAt(int index) {
			return table.getImagesManager().getImages(index);
		}

		/**
		 * Число элементов
		 */
        @Override
		public int getSize() {
			return table.getModel().getRowCount();
		}
	}
}
