package consolidation.forms.rules.base;

/**
 * ��������� ��� ������� � ������ � �������� ����������� ������������ 
 * @author �����
 *
 */
public interface LineValues extends Values{

	/**
	 * �������� �������� ������
	 * @param row ����� ������
	 */
	public Object getValue(int row);

	/**
	 * ��������� �������� ������
	 * @param row ����� ������
	 * @param value ��������
	 */
	public void setValue(int row, Object value); 

}
