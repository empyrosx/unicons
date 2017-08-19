package consolidation.forms.table;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import consolidation.forms.model.TableInfoModel;
import ru.diman.description.Column;
import ru.diman.description.column.types.ColumnType;
import ru.diman.system.handbooks.HandBookEditor;

public class TableKeyListener implements KeyListener{

	// таблица
	private JTable table;
	
	/**
	 *  онструктор
	 */
	public TableKeyListener(JTable table) {
		this.table = table;	
	}
	
	/**
	 * ќбработчик на нажатие кнопки
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){

		/**
		 * ќтключаем вход в режим редактировани€ по Escape
		 */
		case KeyEvent.VK_ESCAPE:
			e.consume();
			break;
		
		/**
		 * ƒобавл€ем обнуление €чейки по DELETE и обнуление значений в строке по Ctrl + DELETE 
		 */
		case KeyEvent.VK_DELETE:

			// обнуление €чейки
			if (! (e.isAltDown() | e.isControlDown() | e.isShiftDown())) {

				// обнуление €чейки по DELETE
				
				// получаем реальные индексы €чейки в модели
				int columnAtModel = table.convertColumnIndexToModel(table.getSelectedColumn());
				int rowAtModel = table.convertRowIndexToModel(table.getSelectedRow());
				
				// обнул€ем значение
				table.getModel().setValueAt(null, rowAtModel, columnAtModel);

				// помечание событие как обработанное
				e.consume();
			}
			else if (e.isControlDown()) {

				// обнуление значений в строке по Ctrl + DELETE
				
				// получаем реальный индекс строки в модели
				int rowAtModel = table.convertRowIndexToModel(table.getSelectedRow());

				// получаем модель
				TableInfoModel model = (TableInfoModel) table.getModel();

				// обнул€ем значени€ в строке
				for (int i = 0; i < model.getColumnCount(); i++) {
					Column ci = model.getColumn(i);
					if (ci.getColumnType() == ColumnType.VALUE){
						model.setValueAt(null, rowAtModel, i);
					}
				} 

				// помечание событие как обработанное
				e.consume();
			}
			break;
		
		case KeyEvent.VK_ENTER:
			if (e.isControlDown()){
				
				// вызываем редактирование колонки
				if (table.editCellAt(table.getSelectedRow(), table.getSelectedColumn())){
					
					TableCellEditor editor = table.getCellEditor(table.getSelectedRow(), table.getSelectedColumn());
					if (editor != null){
						if (editor instanceof HandBookEditor){
							
							HandBookEditor hb = (HandBookEditor)editor;
							hb.edit();
							e.setKeyCode(0);
						};
					}
				}
			}
			
		break;
		
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
