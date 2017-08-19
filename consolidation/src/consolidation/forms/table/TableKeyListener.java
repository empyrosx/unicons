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

	// �������
	private JTable table;
	
	/**
	 * �����������
	 */
	public TableKeyListener(JTable table) {
		this.table = table;	
	}
	
	/**
	 * ���������� �� ������� ������
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){

		/**
		 * ��������� ���� � ����� �������������� �� Escape
		 */
		case KeyEvent.VK_ESCAPE:
			e.consume();
			break;
		
		/**
		 * ��������� ��������� ������ �� DELETE � ��������� �������� � ������ �� Ctrl + DELETE 
		 */
		case KeyEvent.VK_DELETE:

			// ��������� ������
			if (! (e.isAltDown() | e.isControlDown() | e.isShiftDown())) {

				// ��������� ������ �� DELETE
				
				// �������� �������� ������� ������ � ������
				int columnAtModel = table.convertColumnIndexToModel(table.getSelectedColumn());
				int rowAtModel = table.convertRowIndexToModel(table.getSelectedRow());
				
				// �������� ��������
				table.getModel().setValueAt(null, rowAtModel, columnAtModel);

				// ��������� ������� ��� ������������
				e.consume();
			}
			else if (e.isControlDown()) {

				// ��������� �������� � ������ �� Ctrl + DELETE
				
				// �������� �������� ������ ������ � ������
				int rowAtModel = table.convertRowIndexToModel(table.getSelectedRow());

				// �������� ������
				TableInfoModel model = (TableInfoModel) table.getModel();

				// �������� �������� � ������
				for (int i = 0; i < model.getColumnCount(); i++) {
					Column ci = model.getColumn(i);
					if (ci.getColumnType() == ColumnType.VALUE){
						model.setValueAt(null, rowAtModel, i);
					}
				} 

				// ��������� ������� ��� ������������
				e.consume();
			}
			break;
		
		case KeyEvent.VK_ENTER:
			if (e.isControlDown()){
				
				// �������� �������������� �������
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
