package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleImpl;
import consolidation.forms.rules.base.RowValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * ����� ��� ������� ����� ����� ��������� ����� ����
 * 
 * <pre>���������� ��� ���� �����:
 *    1. ������ �����������
 *       ��������:
 *         A = B + C
 *    2. �������������� ������
 *       ��������:
 *         A := B + C
 *    ������� ��������������� ������� ������������ ����� ��� �������� ������ � �������� "������ �����������" 
 *   
 * @author �����
 * 
 * </pre>
 *
 */
public class RowRuleImpl extends BaseRuleImpl<RowRuleItem, RowValues> implements RowRule{

	/**
	 * �������� ������� "���������� �������"
	 * @param ruleCaption ��������� ������������ �����������
	 * @param leftPart ������� ����� ����� ������������ �����������
	 */
	public RowRuleImpl(String ruleCaption, RowRuleItem leftPart){
		super(ruleCaption, leftPart);
	}

	/**
	 * �������� ������� "���������� �������"
	 * @param ruleCaption ��������� ������������ �����������
	 * @param columnName ��� ������� � ����� ����� ������������ �����������
	 * @param ruleSign ���� ������������ �����������
	 */
	public RowRuleImpl(String ruleCaption, String columnName, RuleSign ruleSign){
		super(ruleCaption, new RowRuleItemImpl(columnName, ruleSign));
	}
	
	/**
	 * ��������� ����� �������, ������� � ����� ����� ������������ �����������
	 */
	public String getColumnName() {
		
		// �������� ������� ����� ����� ������������ �����������
		RowRuleItem ruleItem = iterator().next();
		
		// ���������� ��� ������� ����� ��������
		return ruleItem.getColumnName();
	}
	
	/**
	 * ���������� �������� ���������� ������������ �����������
	 * @param columnName ��� ������� ��������
	 * @param ruleSign ���� �������� ������������ �����������
	 */
	public void add(String columnName, RuleSign ruleSign){
		super.add(new RowRuleItemImpl(columnName, ruleSign));
	}

	/**
	 * ��������� �������� ����� ����� ������������ �����������
	 */
	protected void setValue(RowValues values, Double value) {
		// ��������� ��������
		values.setValue(getColumnName(), value);
	}

}
