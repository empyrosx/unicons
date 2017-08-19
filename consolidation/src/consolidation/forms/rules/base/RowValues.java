package consolidation.forms.rules.base;

/**
 * ��������� ��� ������� � ������ � ��������� ����������� ������������ 
 * @author �����
 *
 */

public interface RowValues extends Values{

	/**
	 * ��������� �������� �������
	 * @param columnName ��� �������
	 */
	public Object getValue(String columnName);
	
	/**
	 * ��������� �������� �������
	 * @param columnName ��� �������
	 * @param value ��������
	 */
	public void setValue(String columnName, Object value);

}
