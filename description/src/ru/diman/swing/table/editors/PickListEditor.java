package ru.diman.swing.table.editors;

import ru.diman.swing.table.*;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import ru.diman.swing.table.editors.PickList.PickItem;

/**
 * Класс, представляющий собой редактор колонок с выпадающим списком
 * @author Димитрий
 *
 */
public class PickListEditor extends DefaultCellEditor{

	private static final long serialVersionUID = 9142664409784279978L;

	// компонент
	JComboBox editor;

	// выпадающий список
	private PickList pickList;

	/**
	 * Создание редактора
	 * @param pickList выпадающий список
	 *
	 */
    public PickListEditor(PickList pickList) {
    	super(new JComboBox());
    	this.editor = (JComboBox)editorComponent;
    	this.pickList = pickList;
    	setClickCountToStart(2);

    	// добавляем элементы из выпадающего списка в редактор
    	Iterator<PickItem> pickItems = pickList.getIterator();
    	while (pickItems.hasNext()){
    		editor.addItem(pickItems.next());
    	}

	}

	// отображение редактора
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

    	// ищем элемент в выпадающем списке
    	Object pickItem = pickList.findItem(value);

    	// выделяем его
    	editor.setSelectedItem(pickItem);

    	return editorComponent;
    }

    /**
     * Получение выбранного значения
     */
    @Override
    public Object getCellEditorValue(){

    	// получаем текущий элемент
    	Object pickItem = editor.getSelectedItem();

    	// получаем реальное значение
    	Object realValue = pickList.getRealValue(pickItem);

    	return realValue;
    }

    /**
     * Показать выпадающий список
     */
    public void edit(){
    	//editor.showPopup();
    	//seditor.invalidate();
    	//editor.repaint();
    	//editor.showPopup();
    	editor.actionPerformed(null);
    }

}
