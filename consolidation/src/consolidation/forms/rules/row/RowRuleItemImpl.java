package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleItemImpl;
import consolidation.forms.rules.base.RowValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * ���������� �������� ���������� ������������ �����������
 * @author �����
 *
 */
public class RowRuleItemImpl extends BaseRuleItemImpl<RowValues> implements RowRuleItem{

	// ��� ������� 
	private String columnName;

	/**
	 * �������� �������� ���������� ������������ �����������
	 * @param columnName ��� ������� �������� 
	 * @param ruleSign ���� ��������
	 */
	public RowRuleItemImpl(String columnName, RuleSign ruleSign) {
		super(ruleSign);
		this.columnName = columnName;
	}

	/**
	 * ��������� ����� ������� �������� ������������ �����������
	 */
	public String getColumnName(){
		return columnName;
	}

	public Object getValue(RowValues values) {
		Object objValue = values.getValue(columnName);
		if (objValue != null){
			
			// ��������� � �������
			return objValue;
		}
		else {
			return new Double(0.0);
		}
	}

}
