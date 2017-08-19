package consolidation.forms.rules.base;


/**
 * ���������, ������������ ������� ������� ������������ �����������
 *   
 * @author �����
 * @param <M> ��������� ������� � ������ ��� ������� ��������
 *
 */
public interface BaseRuleItem<M extends Values> {
	
	/**
	 * ��������� ����� ������������ �����������
	 */	
	public RuleSign getRuleSign();
	
	/**
	 * ��������� �������� �������� ������������ �����������
	 * @param values ������ ��� ��������� ��������
	 */
	public Object getValue(M values);
}
