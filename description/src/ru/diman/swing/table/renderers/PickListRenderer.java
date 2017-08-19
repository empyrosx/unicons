package ru.diman.swing.table.renderers;

import ru.diman.swing.table.*;
import ru.diman.swing.table.editors.PickList;
import javax.swing.JComponent;

/**
 * Класс декоратора, отвечающий за декорирование данных колонки с выпадающим списком
 * @author Димитрий
 *
 */

public class PickListRenderer implements ColumnRenderer{

	// выпадающий список
	PickList pickList;

	/**
	 * Создание
	 * @param pickList выпадающий список
	 */
	public PickListRenderer(PickList pickList){
		this.pickList = pickList;
	}

	/**
	 * Получение приукрашенного значения
     * @return
     */
    @Override
	public Object renderValue(Object value) {
		return pickList.getVisualValue(value);
	}

    @Override
	public void prepareRenderer(JComponent renderer) {
	}
}
