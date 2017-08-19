package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRule;
import consolidation.forms.rules.base.RowValues;

/**
 * ��������� c��������� ������������ �����������
 * @author �����
 *
 */
public interface RowRule extends BaseRule<RowRuleItem, RowValues>{
	
	/**
	 * ��������� ����� �������, ������� � ����� ����� ������������ �����������
	 */
	public String getColumnName();
}
