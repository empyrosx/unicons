package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleItem;
import consolidation.forms.rules.base.LineValues;

/**
 * ��������� �������� ��������� ������������ �����������
 * @author �����
 *
 */
public interface LineRuleItem extends BaseRuleItem<LineValues> {

    /**
     * ��������� ������ ������ �������� ��������� ������������ �����������
     * @return
     */
    public int getRow();
}
