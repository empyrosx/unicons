/**
 * 
 */
package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleItem;
import consolidation.forms.rules.base.RowValues;

/**
 * ��������� �������� ���������� ������������ �����������
 * @author �����
 *
 */
public interface RowRuleItem extends BaseRuleItem<RowValues>{

	/**
	 * ��������� ����� ������� �������� ������������ �����������
	 */
	public String getColumnName();
	
}
