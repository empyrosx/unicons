package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRule;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.line.LineRuleItem;

/**
 * ��������� ��������� ������������ �����������
 * @author �����
 *
 */
public interface LineRule extends BaseRule<LineRuleItem, LineValues>{
    
    /**
     * ��������� ������ ������, ������� � ����� ����� ������������ �����������
     */
    public int getRow();
    
}
