package consolidation.database;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4460351311532806316L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		
		this.setIcon(null);
		this.setText("");
		this.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (table != null){
			if (value != null){
				String strValue = value.toString();
				if (!strValue.isEmpty()){
					Integer intValue = 0;
					try{
						intValue = new Integer(strValue);

						switch (intValue.intValue()){
						case 1:
							this.setIcon(new ImageIcon("D:/Templates/red.gif"));
						}
					}
					catch (NumberFormatException e){
					};
				}
			}
		}
		return this;
	}

}
