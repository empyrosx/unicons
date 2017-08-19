package consolidation.forms.rules.base;

import consolidation.forms.model.TableInfoModel;
import consolidation.forms.rules.base.Values;


/**
 * ���������� ������� ��� ������� � ������
 * @author �����
 *
 */
public class ValuesImpl implements Values, RowValues, LineValues{

	// ������ ������ 
	TableInfoModel model;
	
	// ����� ������� ������
	private int rowIndex;
	
	// ��� ������� �������
	private String columnName;

	/**
	 * �������� ������� ������� � ������ ��� ������� ������
	 * @param model ������ ������
	 * @param rowIndex ����� ������� ������
	 */
	public ValuesImpl(TableInfoModel model, int rowIndex){
		this.model = model;
		this.rowIndex = rowIndex;
	}

	/**
	 * �������� ������� ������� � ������ ��� ������� �������
	 * @param model ������ ������
	 * @param columnName ��� ������� �������
	 */
	public ValuesImpl(TableInfoModel model, String columnName){
		this.model = model;
		this.columnName = columnName;
	}

	/**
	 * ��������� �������� �������
	 * @param columnName ��� �������
	 */
	public Object getValue(String columnName) {
		return model.getValue(rowIndex, columnName);
	}

	/**
	 * �������� �������� ������
	 * @param row ����� ������
	 */
	public Object getValue(int row) {
		return model.getValue(row, columnName);
	}

	/**
	 * ��������� �������� �������
	 * @param columnName ��� �������
	 * @param value ��������
	 */
	public void setValue(String columnName, Object value) {
		
		if ((value instanceof Double) && (value.equals(0.0))){
			model.setValueAt(null, rowIndex, columnName);
		}
		else{
			model.setValueAt(value, rowIndex, columnName);
		}
	}

	/**
	 * ��������� �������� ������
	 * @param row ����� ������
	 * @param value ��������
	 */	
	public void setValue(int row, Object value) {
		if ((value instanceof Double) && (value.equals(0.0))){
			model.setValueAt(null, row, columnName);
		}
		else{
			model.setValueAt(value, row, columnName);
		}
	}
}
